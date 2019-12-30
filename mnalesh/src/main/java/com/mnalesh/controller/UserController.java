package com.mnalesh.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mnalesh.entity.User;
import com.mnalesh.repository.UserRepository;
import com.mnalesh.service.UserService;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserController {
	@Autowired
    AuthenticationTrustResolver authenticationTrustResolver;
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	   UserService userService;
	@Autowired
	MessageSource messageSource;

	// this is the login api/service
	@GetMapping("/home")
	public User user(Principal principal) {
		User user=new User();
		 user = userRepository.findBySSO("sam").get();
			return user;
		}
			
		
@PostMapping("/login")
	public User userpost(@RequestParam String ssoId,Principal principal) {
		if (ssoId !=null) {
			User user=new User();
			  user = userRepository.findBySSO(principal.getName()).get();
			return user;
		

		} else {
			User user=new User();
			user.setId(4L);
			return user;	
		}
			

		}
	
}
