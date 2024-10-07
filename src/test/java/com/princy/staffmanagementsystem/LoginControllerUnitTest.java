package com.princy.staffmanagementsystem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.princy.staffmanagementsystem.controller.LoginController;
import com.princy.staffmanagementsystem.service.UserService;

@WebMvcTest(controllers = LoginController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class LoginControllerUnitTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean
	private UserService userService;

	@Test
	public void testRegisterUser_Success() throws Exception {
		Mockito.when(passwordEncoder.encode("princy")).thenReturn("encodePassword");

		mockMvc.perform(post("/register").param("name", "princy").param("email", "princy@gmail.com")
				.param("password", "password123").param("confirmPassword", "password123"))
				.andExpect(status().is3xxRedirection()) // Expect a redirection
				.andExpect(redirectedUrl("/login"));
	}

	@Test
	public void testRegisterUser_PasswordMismatch() throws Exception {
		mockMvc.perform(post("/register").param("name", "princy").param("email", "princy@gmail.com")
				.param("password", "password1234").param("confirmPassword", "password123")).andExpect(status().isOk())
				.andExpect(model().attribute("error", "Passwords do not match."));

	}
}