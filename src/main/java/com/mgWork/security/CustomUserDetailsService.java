package com.mgWork.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mgWork.expensetrackerapi.entity.User;
import com.mgWork.expensetrackerapi.repository.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepo;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User existingUser = userRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("user not found for the email : " + email));
		return new org.springframework.security.core.userdetails.User(existingUser.getEmail(),
				existingUser.getPassword(), new ArrayList<>());
	}

}
