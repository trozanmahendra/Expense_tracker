package com.mgWork.expensetrackerapi.service;

import com.mgWork.expensetrackerapi.entity.User;
import com.mgWork.expensetrackerapi.entity.UserModel;
import com.mgWork.expensetrackerapi.exception.ResourceNotFoundException;

public interface UserService {

	User createuser(UserModel user);
	
	User readuser() throws ResourceNotFoundException;
	
	User updateUser(UserModel model) throws ResourceNotFoundException;
	
	void deleteUser() throws ResourceNotFoundException;
	
	User getLoggedInUser();
	
}
