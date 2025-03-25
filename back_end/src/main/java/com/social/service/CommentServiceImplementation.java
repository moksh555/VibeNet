package com.social.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Comment;
import com.social.models.Post;
import com.social.models.User;
import com.social.repository.CommentRepository;
import com.social.repository.PostRepository;

@Service
public class CommentServiceImplementation implements CommentService{

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private PostService postService;
	
	@Override
	public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {
		
		User user = userService.findUserById(userId);
		
		Post post = postService.findPostById(postId);
		
		comment.setContent(comment.getContent());
		comment.setUser(user);
		comment.setCeratedAt(LocalDateTime.now());
		commentRepository.save(comment);
		
		post.getComments().add(comment);
		
		postRepository.save(post);
		return comment;
	}

	@Override
	public Comment likeComment(Integer commentId, Integer userId) throws Exception {
		Comment comment = findCommentById(commentId);
		User user = userService.findUserById(userId);
		
		if (comment.getLiked().contains(user)) {
			comment.getLiked().remove(user);
		}else {
			comment.getLiked().add(user);
		}
		
		commentRepository.save(comment);
		return comment;
	}

	@Override
	public Comment findCommentById(Integer commentId) throws Exception {
		
		Optional<Comment> comment = commentRepository.findById(commentId);
		
		if (comment.isEmpty()) {
			throw new Exception("Cannot find this email!");
		}
		
		return comment.get();
	}

}
