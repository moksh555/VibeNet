package com.social.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Post;
import com.social.models.User;
import com.social.repository.PostRepository;
import com.social.repository.UserRepository;

@Service
public class PostServiceImplementation implements PostService{
	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserService userService;
	
	@Override
	public Post createNewPost(Post post, Integer userId) throws Exception {
		
		User user = userService.findUserById(userId);
		
		
		Post newPost = new Post();
		newPost.setCaption(post.getCaption());
		newPost.setImage(post.getImage());
		newPost.setVideo(post.getVideo());
		newPost.setUser(user);
		newPost.setCreatedAt(LocalDateTime.now());
		
		postRepository.save(newPost);
		return newPost;
	}

	@Override
	public String deletePost(Integer postId, Integer userId) throws Exception {
		Post validPost = findPostById(postId);
		User user = userService.findUserById(userId);
		
		if (validPost.getUser().getId() != user.getId()) {
			throw new Exception("You cannot delete someone post");
		}
		
		postRepository.delete(validPost);
		
		return "Post deleted successfully";
	}

	@Override
	public List<Post> findPostByUserId(Integer userId) throws Exception {
		List<Post> allPosts = postRepository.findPostByUserId(userId);
		
		return allPosts;
	}

	@Override
	public Post findPostById(Integer postId) throws Exception{
		Optional<Post> allPosts = postRepository.findById(postId);
		
		if (allPosts.isEmpty()) {
			throw new Exception("No post with postId " + postId);
			
		}
		return allPosts.get();
		
	}

	@Override
	public List<Post> findAllPost() {
		List<Post> allPosts = postRepository.findAll();
		return allPosts;
	}

	@Override
	public Post savedPost(Integer postId, Integer userId) throws Exception {
		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		
		if (user.getSavedPost().contains(post)) {
			user.getSavedPost().remove(post);
		}else {
			user.getSavedPost().add(post);
		}
		
		userRepository.save(user);
		return post;
	}

	@Override
	public Post likePost(Integer postId, Integer userId) throws Exception {
		Post likedPost = findPostById(postId);
		User user = userService.findUserById(userId);
		
		if (likedPost.getLiked().contains(user)) {
			likedPost.getLiked().remove(user);
		}else {
			likedPost.getLiked().add(user);
		}
		
		postRepository.save(likedPost);
		
		return likedPost; 
	}

}
