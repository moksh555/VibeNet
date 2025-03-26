package com.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Reels;
import com.social.models.User;
import com.social.repository.ReelsRepository;

@Service
public class ReelsServiceImplementation implements ReelsService {
	
	@Autowired
	ReelsRepository reelsRepository;
	
	@Autowired
	UserService userService;
	
	@Override
	public Reels createReel(Reels reel, User user) {
		Reels createReel = new Reels();
		createReel.setTitle(reel.getTitle());
		createReel.setUser(user);
		createReel.setVideo(reel.getVideo());;
		reelsRepository.save(createReel);
		return createReel;
	}

	@Override
	public List<Reels> findAllReels() {
		List<Reels> allReels = reelsRepository.findAll();
		
		return allReels;
	}

	@Override
	public List<Reels> findUserReel(Integer userId) throws Exception {
		User user = userService.findUserById(userId);
		List<Reels> reels = reelsRepository.findByUserId(userId);
		return reels;
	}

}
