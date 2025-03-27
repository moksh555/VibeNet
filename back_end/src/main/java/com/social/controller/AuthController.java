package com.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.social.config.JwtProvider;
import com.social.models.User;
import com.social.repository.UserRepository;
import com.social.request.LoginRequest;
import com.social.response.AuthResponse;
import com.social.service.CustomerUserDetailService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	

	@ Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomerUserDetailService customerUserDetails;
	
	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody User user) throws Exception {
		
		User isAlready = userRepository.findByEmail(user.getEmail());
		
		if (isAlready != null) {
			throw new Exception("The email is already in used with another account!");
		}
		
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setGender(user.getGender());
		
		User savedUser = userRepository.save(newUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		
		String token = JwtProvider.generateToken(authentication);
		
		AuthResponse res=new AuthResponse(token, "Register Success");
		
		return res;
	}
	
	@PostMapping("/signin")
	public AuthResponse loginUser(@RequestBody LoginRequest loginRequest) {
		System.out.println(loginRequest);
		Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		String token = JwtProvider.generateToken(authentication);
		
		AuthResponse res=new AuthResponse(token, "Login Success");
		
		return res;
	}

	private Authentication authenticate(String email, String password) {
		UserDetails userDetails = customerUserDetails.loadUserByUsername(email);
		
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid username");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword()) ) {
			throw new BadCredentialsException("Invalid username and password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}

