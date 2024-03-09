package com.groofycode.GroofyCode.controller;
import com.groofycode.GroofyCode.dto.PostDTO;
import com.groofycode.GroofyCode.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.createPost(postDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    public ResponseEntity<?> updatePostById(@PathVariable Long postId, @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = postService.updatePostById(postId, postDTO);
        if (updatedPost == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to update this post");
        }
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserPosts(@PathVariable Long userId) {
        // Implement getUserPosts logic in your service layer
        List<PostDTO> userPosts = postService.getUserPosts(userId);
        return new ResponseEntity<>(userPosts, HttpStatus.OK);
    }
//
//    @PostMapping("/likes/{postId}")
//    public ResponseEntity<?> addLike(@PathVariable Long postId, @RequestBody Long userId) {
//        // Implement addLike logic in your service layer
//        postService.addLike(postId, userId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
