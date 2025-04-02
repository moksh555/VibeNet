package com.social.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.exceptions.UserException;
import com.social.models.User;
import com.social.repository.UserRepository;
import com.social.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	

	
	@GetMapping("/api/users")
	public List<User> getUsers() {
		List<User> allUsersFound = userService.getAllUsers();
		return allUsersFound;
	}
	
	@GetMapping("/api/users/{userId}")
	public User getUserById(@PathVariable("userId") Integer id) throws UserException{
		User user  = userService.findUserById(id);
		return user;
	} 
	

	
	@PutMapping("/api/users")
	public User updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) throws UserException {
		User reqUser = userService.findUserByJwt(jwt);
		User updatedUser = userService.updateUser(reqUser, reqUser.getId());
		return updatedUser;
		
	}
	
	@DeleteMapping("/api/users/{userId}")
	public String deleteUser(@PathVariable("userId") Integer id) throws UserException{
		Optional<User> user = userRepository.findById(id);
		
		if (user.isPresent()) {
			userRepository.deleteById(id);
			return "user " + id.toString() + " Deleted";
		}
		
		throw new UserException("User with Id " + id + " Does not exists");
	}
	
	@PutMapping("/api/users/follow/{userId2}")
	public User followUserHandler(@RequestHeader("Authorization") String jwt ,@PathVariable Integer userId2) throws UserException {
		User reqUser = userService.findUserByJwt(jwt);
		User user = userService.followUser(reqUser.getId(), userId2);
		return user;
	}
	
	@GetMapping("/api/users/search")
	public List<User> searchUser(@RequestParam("query") String query){
		List<User> searchResults = userService.searchUser(query);
		return searchResults;
	}
		
	@GetMapping("/api/users/profile")
	public User getUserFromToken(@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserByJwt(jwt);
		return user;
				
	}
}
