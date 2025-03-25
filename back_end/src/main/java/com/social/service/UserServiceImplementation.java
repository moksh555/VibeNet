package com.social.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.config.JwtProvider;
import com.social.models.User;
import com.social.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User registerUser(User user) {
		return null;
	}
	
	@Override
	public List<User> getAllUsers(){
		List<User> allUsers = userRepository.findAll();
		return allUsers;
	}

	@Override
	public User findUserById(Integer userId) throws Exception{
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			return user.get();
		}
		
		throw new Exception("user does not exixts " + userId);
	}

	@Override
	public User findUserByEmail(String email) {
		
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public User followUser(Integer reqUserId, Integer folUserId) throws Exception {
		User reqUser = findUserById(reqUserId);
		
		User folUser = findUserById(folUserId);
		
		reqUser.getFollowing().add(folUser.getId());
		folUser.getFollowers().add(reqUser.getId());
		
		userRepository.save(reqUser);
		userRepository.save(folUser);
		return reqUser;
	}

	@Override
	public User updateUser(User user, Integer id) throws Exception{
		Optional<User> userUpdate = userRepository.findById(id);
		
		if (userUpdate.isEmpty()){
			throw new Exception("user does not exists with Id " + id);
		} 
		User existingUser = userUpdate.get();
		if (user.getFirstName() != null) {
			existingUser.setFirstName(user.getFirstName());
		}
		
		if (user.getLastName() != null) {
			existingUser.setLastName(user.getLastName());
		}
		
		if (user.getEmail() != null) {
			existingUser.setEmail(user.getEmail());
		}
		if (user.getPassword() != null) {
			existingUser.setPassword(user.getPassword());
		}
		if (user.getGender() != null) {
			existingUser.setGender(user.getGender());
		} 
		
		
		
		userRepository.save(existingUser);
		return existingUser;

		
	}

	@Override
	public List<User> searchUser(String query) {
		List<User> results = userRepository.searchUser(query);
		return results;
	}

	@Override
	public User findUserByJwt(String jwt) {
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		
		User user = userRepository.findByEmail(email);
		user.setPassword(null);
		return user;
	}
	
	

}
