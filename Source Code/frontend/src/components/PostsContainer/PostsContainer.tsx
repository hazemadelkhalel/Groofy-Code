import { useEffect, useState } from "react";
import { GBtn, GroofyTA, Posts, ProfileImage } from "..";
import { useDispatch, useSelector } from "react-redux";
import postThunks from "../../store/actions/post-actions";
import { PostsContainerProps } from "../../shared/types";
import classes from "./scss/posts-container.module.css";

const PostsContainer = ({ user, toast, self }: PostsContainerProps) => {
  const dispatch = useDispatch();
  const resStatus = useSelector((state: any) => state.post.status);
  const resMessage = useSelector((state: any) => state.post.message);
  const allPosts: any[] = useSelector((state: any) => state.post.body);
  const loggedUser = useSelector((state: any) => state.auth.user);
  const [newPostContent, setNewPostContent] = useState("");

  const postHandler = (event: any) => {
    event.preventDefault();
    const addNewPost = async () => {
      await dispatch(postThunks.addPost(newPostContent) as any);
    };
    if (newPostContent.trim() === "") {
      (toast.current as any)?.show({
        severity: "info",
        summary: "Info",
        detail: "The post is empty",
        life: 3000,
      });
      return;
    }
    addNewPost();
  };

  useEffect(() => {
    const fetchPosts = async () => {
      await dispatch(postThunks.getPosts(user.id) as any);
    };
    fetchPosts();
  }, [dispatch, user]);

  useEffect(() => {
    if (
      resStatus === "" ||
      resMessage === "" ||
      resMessage === "User posts retrieved successfully"
    )
      return;
    if (resStatus === "success") {
      (toast.current as any)?.show({
        severity: "success",
        summary: "Success",
        detail: resMessage,
        life: 3000,
      });
      setNewPostContent("");
    } else {
      (toast.current as any)?.show({
        severity: "error",
        summary: "Failed",
        detail: resMessage,
        life: 3000,
      });
    }
  }, [allPosts.length, resMessage, resStatus, toast]);

  return (
    <form className={classes.posts_container} onSubmit={postHandler}>
      <div
        className={`${classes.post_box} ${
          self ? "" : loggedUser.username !== user.username && classes.false
        }`}
      >
        {self && user && (
          <div className={classes.post_row}>
            <ProfileImage
              photoUrl={loggedUser.photoUrl}
              username={loggedUser.username}
              style={{
                backgroundColor: loggedUser.accountColor,
                width: "50px",
                height: "50px",
                marginRight: "10px",
                fontSize: "20px",
              }}
              canClick={false}
            />
            <GroofyTA
              taValue={newPostContent}
              changeHandler={setNewPostContent}
            />
            <GBtn
              btnText="Quick Post"
              icnSrc="/Assets/SVG/quick.svg"
              clickEvent={() => {}}
              btnType={true}
            />
          </div>
        )}
        {!self && loggedUser.username === user.username && (
          <div className={classes.post_row}>
            <ProfileImage
              photoUrl={loggedUser.photoUrl}
              username={loggedUser.username}
              style={{
                backgroundColor: loggedUser.accountColor,
                width: "50px",
                height: "50px",
                marginRight: "10px",
                fontSize: "20px",
              }}
              canClick={false}
            />
            <GroofyTA
              taValue={newPostContent}
              changeHandler={setNewPostContent}
            />
            <GBtn
              btnText="Quick Post"
              icnSrc="/Assets/SVG/quick.svg"
              clickEvent={() => {}}
              btnType={true}
            />
          </div>
        )}
        <Posts posts={allPosts} user={user} loggedUser={loggedUser.id} />
      </div>
    </form>
  );
};

export default PostsContainer;
