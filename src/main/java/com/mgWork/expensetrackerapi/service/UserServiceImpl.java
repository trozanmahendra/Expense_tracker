package com.mgWork.expensetrackerapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mgWork.expensetrackerapi.entity.User;
import com.mgWork.expensetrackerapi.entity.UserModel;
import com.mgWork.expensetrackerapi.exception.ItemAlreadyExistsException;
import com.mgWork.expensetrackerapi.exception.ResourceNotFoundException;
import com.mgWork.expensetrackerapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository repository;
	private User newUser;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository repository, User newUser, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.newUser = newUser;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User createuser(UserModel user) {
		if (repository.existsByEmail(user.getEmail()))
			throw new ItemAlreadyExistsException("User details are already existing : " + user.getEmail());
//		User newUser = new User();
		newUser.setId(null);
		BeanUtils.copyProperties(user, newUser);
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		return repository.saveAndFlush(newUser);
	}

	@Override
	public User readuser() throws ResourceNotFoundException {
		Long id = getLoggedInUser().getId();
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found of id : " + id));
	}

	@Override
	public User updateUser(UserModel model) throws ResourceNotFoundException {
		User oUser = readuser();
		oUser.setName(model.getName() != null ? model.getName() : oUser.getName());
		oUser.setAge(model.getAge() != null ? model.getAge() : oUser.getAge());
		oUser.setEmail(model.getEmail() != null ? model.getEmail() : oUser.getEmail());
		oUser.setPassword(
				model.getPassword() != null ? passwordEncoder.encode(model.getPassword()) : oUser.getPassword());
		return repository.save(oUser);
	}

	@Override
	public void deleteUser() throws ResourceNotFoundException {
		User oUser = readuser();
		repository.delete(oUser);
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email =authentication.getName();
		return repository.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("user not found for the email : "+email));
	}
	
	
}





























