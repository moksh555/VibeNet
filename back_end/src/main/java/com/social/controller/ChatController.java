package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Chat;
import com.social.models.User;
import com.social.request.CreateChatRequest;
import com.social.service.ChatService;
import com.social.service.UserService;

@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/chat")
	public Chat createChat(@RequestBody CreateChatRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
		User reqUser = userService.findUserByJwt(jwt);
		User user2 = userService.findUserById(req.getUserId());
		Chat chat = chatService.createChat(reqUser, user2);
		return chat;
	}
	
	
	@GetMapping("/api/chat")
	public List<Chat> findusersChat(@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserByJwt(jwt);
		
		List<Chat> chat = chatService.findUserChat(user.getId());
		return chat;
	}
	
}
