package com.princy.staffmanagementsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.princy.staffmanagementsystem.entity.User;
import com.princy.staffmanagementsystem.enums.Role;
import com.princy.staffmanagementsystem.repository.UserRepository;
import com.princy.staffmanagementsystem.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Display list of all users
	@GetMapping("/")
	public String listUsers(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "admin/users/index"; // Returns to the list view of users
	}

	// Display the form to add a new user
	@GetMapping("/add")
	public String createUser(Model model) {
		model.addAttribute("user", new User());
		return "admin/users/create"; // Returns the create user form
	}

	@PostMapping("/create")
	public String addUser(@ModelAttribute User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setRole(Role.ADMIN);
		userService.saveUser(user);
		return "redirect:/admin/users/"; // Redirects to the user list after creation
	}

	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable Long id, Model model) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			model.addAttribute("user", user);
			return "admin/users/edit"; // Returns the edit user form
		}
		return "redirect:/admin/users"; // Redirects to user list if ID not found
	}

	// Handle the update of an existing user
	@PostMapping("/edit")
	public String updateUser(@ModelAttribute User user) {
		Optional<User> existingUser = userRepository.findById(user.getId());
		if (existingUser.isPresent()) {
			User userToUpdate = existingUser.get();
			userToUpdate.setName(user.getName());
			userToUpdate.setEmail(user.getEmail());
			userToUpdate.setUsername(user.getUsername());

			// Only encode and update the password if it was changed

			userService.saveUser(userToUpdate); // Save the updated user
		}
		return "redirect:/admin/users/"; // Redirects to the user list after update
	}

	// Handle the deletion of a user
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			userRepository.delete(user.get());
		}
		return "redirect:/admin/users/"; // Redirects to the user list after deletion
	}
}
