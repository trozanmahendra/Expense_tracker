package com.mgWork.expensetrackerapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mgWork.expensetrackerapi.entity.AuthModel;
import com.mgWork.expensetrackerapi.entity.Jwtresponse;
import com.mgWork.expensetrackerapi.entity.User;
import com.mgWork.expensetrackerapi.entity.UserModel;
import com.mgWork.expensetrackerapi.service.UserService;
import com.mgWork.expensetrackerapi.util.JwtTokenUtil;
import com.mgWork.security.CustomUserDetailsService;

@RestController
public class AuthController {

	@Autowired
	private UserService service;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<Jwtresponse> login(@RequestBody AuthModel authModel) throws Exception {

		authenticate(authModel.getEmail(), authModel.getPassword());

		final UserDetails details = customUserDetailsService.loadUserByUsername(authModel.getEmail());
		final String token = jwtTokenUtil.generateToken(details);

		return new ResponseEntity<Jwtresponse>(new Jwtresponse(token), HttpStatus.OK);
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		} catch (DisabledException e) {

			throw new Exception("User disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("bad credentals");
		}

	}

	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody UserModel user) {
		return new ResponseEntity<User>(service.createuser(user), HttpStatus.CREATED);
	}
}
