package com.mgWork.expensetrackerapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mgWork.expensetrackerapi.entity.AuthModel;
import com.mgWork.expensetrackerapi.entity.User;
import com.mgWork.expensetrackerapi.entity.UserModel;
import com.mgWork.expensetrackerapi.service.UserService;

@RestController
public class AuthController {
	
	private UserService service;
	private AuthenticationManager authenticationManager;
	
	@Autowired
	public AuthController(UserService service, AuthenticationManager authenticationManager) {
		super();
		this.service = service;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthModel authModel){
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authModel.getEmail()
				, authModel.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
				
		return new ResponseEntity<String>("Welcome "+authModel.getEmail(),HttpStatus.OK) ;
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody UserModel user){
		return new ResponseEntity<User>(service.createuser(user),HttpStatus.CREATED);
	}
}
