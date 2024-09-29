package com.princy.staffmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.princy.staffmanagementsystem.entity.User;
import com.princy.staffmanagementsystem.enums.Role;
import com.princy.staffmanagementsystem.service.UserService;


@Controller
public class LoginController {
	 @Autowired
    private UserService userService; 

    @Autowired
    private PasswordEncoder passwordEncoder;
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user",new User());
		return "login";
	}
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User()); 
        return "register";  
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
    	if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match.");
            return "register"; 
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.ADMIN);
        userService.saveUser(user); 
        return "redirect:/login"; 
    }
}
