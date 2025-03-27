package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Story;
import com.social.models.User;
import com.social.service.StoryService;
import com.social.service.UserService;

@RestController
public class StoryController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StoryService storyService;
	
	
	@PostMapping("/api/story")
	public Story createStory(@RequestBody Story story, @RequestHeader("Authorization") String jwt) {
		User user = userService.findUserByJwt(jwt);
		Story createdStory = storyService.createStory(story, user);
		return createdStory;
	}
	
	@GetMapping("/api/story/{userId}")
	public List<Story> findStoryByUser(@PathVariable Integer userId) throws Exception{
		List<Story> allStory = storyService.findStoryByUser(userId);
		return allStory;
	}

}
