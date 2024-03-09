package com.groofycode.GroofyCode.service;

import com.groofycode.GroofyCode.dto.PostDTO;
import com.groofycode.GroofyCode.model.PostModel;
import com.groofycode.GroofyCode.model.UserModel;
import com.groofycode.GroofyCode.repository.PostRepository;
import com.groofycode.GroofyCode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;


    public PostDTO createPost(PostDTO postDTO) {
        PostModel post = convertToEntity(postDTO);
        post.setCreatedAt(new Date());
        UserModel userModel=userService.getCurrentUser();
        post.setUser(userModel);
        post = postRepository.save(post);
        return convertToDTO(post);
    }


    public PostDTO updatePostById(Long id, PostDTO updatedPostDTO) {
        try {
            Optional<PostModel> optionalPost = postRepository.findById(id);
            if (optionalPost.isEmpty()) {
                return null; // Post not found
            }

            PostModel post = optionalPost.get();
            UserModel currentUser = userService.getCurrentUser();

            // Check if the current user is the owner of the post
            System.out.println(post.getUser());
            System.out.println(post.getUser());

            if (!post.getUser().equals(currentUser)) {
                return null; // Current user is not allowed to update this post
            }

            post.setContent(updatedPostDTO.getContent());
            post.setUpdatedAt(new Date());
            PostModel updatedPost = postRepository.save(post);

            return convertToDTO(updatedPost);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public PostDTO convertToDTO(PostModel post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setUserId(post.getUser().getId()); // Assuming you have getId() method in UserModel
        postDTO.setContent(post.getContent());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUpdatedAt(post.getUpdatedAt());
        // Set other properties
        return postDTO;
    }
    public List<PostDTO> getUserPosts(Long userId) {
        // Fetch posts by user ID from the repository
        List<PostModel> posts = postRepository.findByUserId(userId);
        // Convert PostModel list to PostDTO list
        List<PostDTO> postDTOs = posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return postDTOs;
    }



    private PostModel convertToEntity(PostDTO postDTO) {
        PostModel post = new PostModel();
        post.setContent(postDTO.getContent());
        post.setUpdatedAt(post.getUpdatedAt());
        post.setCreatedAt(post.getCreatedAt());
        return post;
    }
}
