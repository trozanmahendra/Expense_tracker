package com.mgWork.expensetrackerapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mgWork.expensetrackerapi.entity.User;
import com.mgWork.expensetrackerapi.entity.UserModel;
import com.mgWork.expensetrackerapi.exception.ResourceNotFoundException;
import com.mgWork.expensetrackerapi.service.UserService;

@Controller
public class UserController {

	private UserService service;
	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}
	
	@GetMapping("/profile")
	public ResponseEntity<User> readUser() throws ResourceNotFoundException{
		return new ResponseEntity<User>(service.readuser(),HttpStatus.OK);
	}
	@PutMapping("/profile")
	public ResponseEntity<User> updateUser(@RequestBody UserModel model) throws ResourceNotFoundException {
		return new ResponseEntity<User>(service.updateUser(model), HttpStatus.FOUND) ;
	}
	@DeleteMapping("/deactivate")
	public ResponseEntity<HttpStatus> deleteUser() throws ResourceNotFoundException{
		service.deleteUser();
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		
	}
}























