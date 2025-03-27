package com.social.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Story;
import com.social.models.User;
import com.social.repository.StoryRepository;

@Service
public class StoryImplementation implements StoryService{

	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Story createStory(Story story, User user) {
		Story createdStory = new Story();
		
		createdStory.setCaption(story.getCaption());
		createdStory.setImage(story.getImage());
		createdStory.setUser(user);
		createdStory.setTimeStamp(LocalDateTime.now());
		
		storyRepository.save(createdStory);
		
		return createdStory;
	}

	@Override
	public List<Story> findStoryByUser(Integer userId) throws Exception {
		User user = userService.findUserById(userId);
		List<Story> allReels = storyRepository.findByUserId(userId);
		return allReels;
	}

}
