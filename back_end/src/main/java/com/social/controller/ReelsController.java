package com.social.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Reels;
import com.social.models.User;
import com.social.service.ReelsService;
import com.social.service.UserService;

@RestController
public class ReelsController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private ReelsService reelsService;
	
	@PostMapping("/api/reels")
	public Reels createReels(@RequestBody Reels reel, @RequestHeader("Authorization") String jwt) {
		
		User reqUser = userService.findUserByJwt(jwt);
		Reels createdReel = reelsService.createReel(reel, reqUser);
		return createdReel;
	}
	
	@GetMapping("/api/reels")
	public List<Reels> getAllReels(){
		List<Reels> allReels = reelsService.findAllReels();
		return allReels;
	}
	
	@GetMapping("/api/reels/user/{userId}")
	public List<Reels> findUserReels(@PathVariable Integer userId) throws Exception{
		List<Reels> userReels = reelsService.findUserReel(userId);
		return userReels;
	}
}
