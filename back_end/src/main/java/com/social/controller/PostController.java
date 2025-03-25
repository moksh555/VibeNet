package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Post;
import com.social.models.User;
import com.social.response.ApiResponse;
import com.social.service.PostService;
import com.social.service.UserService;

@RestController
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/api/posts")
	public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwt(jwt);
		Post createdPost = postService.createNewPost(post, user.getId());
		return new ResponseEntity<>(createdPost, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/api/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId,@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByEmail(jwt);
		String messageDeleted = postService.deletePost(postId, user.getId());
		
		ApiResponse response = new ApiResponse(messageDeleted, true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<Post> getPostById(@PathVariable Integer postId) throws Exception{
		Post post = postService.findPostById(postId);
		
		return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/posts/user/{userId}")
	public ResponseEntity<List<Post>> getPostByUserId(@PathVariable Integer userId) throws Exception{
		List<Post> allPosts = postService.findPostByUserId(userId);
		
		return new ResponseEntity<>(allPosts, HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<List<Post>> getAllPosts(){
		List<Post> allPosts = postService.findAllPost();
		return new ResponseEntity<>(allPosts, HttpStatus.OK);
	}
	
	@PutMapping("/api/posts/save/{postId}")
	public ResponseEntity<Post> savePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByEmail(jwt);
		Post postOfUser = postService.savedPost(postId, user.getId());
		return new ResponseEntity<>(postOfUser, HttpStatus.OK);
	}
	
	@PutMapping("/api/posts/like/{postId}")
	public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId,@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByEmail(jwt);
		Post userLikedPost = postService.likePost(postId, user.getId());
		return new ResponseEntity<>(userLikedPost, HttpStatus.OK);
	}
}
