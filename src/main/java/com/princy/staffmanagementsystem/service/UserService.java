package com.princy.staffmanagementsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.princy.staffmanagementsystem.entity.User;
import com.princy.staffmanagementsystem.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void softDelete(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			userRepository.save(user);
		}
	}

	public void saveUser(User user) {
		userRepository.save(user); // Save the user in the database
	}

}