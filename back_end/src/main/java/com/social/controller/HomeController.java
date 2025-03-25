package com.social.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping
	public String homeControllerHandler(){
		return "HI";
	}
	
	@GetMapping("/home")
	public String homeControllerHandler2(){
		return "Welcome to home";
	}
	
//	@DeleteMapping
//	public String deleteHome() {
//		
//	}
}
