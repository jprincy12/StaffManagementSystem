package com.princy.staffmanagementsystem;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.princy.staffmanagementsystem.controller.UserController;
import com.princy.staffmanagementsystem.entity.User;
import com.princy.staffmanagementsystem.repository.UserRepository;
import com.princy.staffmanagementsystem.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "a@gmail.com", password = "test@123", roles = { "ADMIN" })
public class UserControllerUnitTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private UserService userService;
	@MockBean
	private PasswordEncoder passwordEncoder;
	@InjectMocks
	private UserController userController;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	void listUsers() throws Exception {
		// Given
		User user1 = new User();
		user1.setId(1L);
		user1.setName("Diksha Thapa");
		user1.setEmail("test@gmail.com");
		user1.setPassword("test");

		User user2 = new User();
		user1.setId(2L);
		user1.setName("Diksha1 Thapa");
		user1.setEmail("tes1t@gmail.com");
		user1.setPassword("test");

		when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

		// When & Then
		mockMvc.perform(get("/admin/users/")).andExpect(status().isOk()).andExpect(view().name("admin/users/index"))
				.andExpect(model().attributeExists("users"));
	}

	@Test
	void showCreateForm() throws Exception {
		mockMvc.perform(get("/admin/users/add")).andExpect(status().isOk()).andExpect(view().name("admin/users/create"))
				.andExpect(model().attributeExists("user"));
	}

	@Test
	void createUser() throws Exception {
		User user = new User();
		user.setName("New user");
		user.setName("Diksha3 Thapa");
		user.setEmail("tes3t@gmail.com");
		user.setPassword("test");

		mockMvc.perform(post("/admin/users/create").param("name", "princy").param("email", "princy@gmail.com")
				.param("password", "password123")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin/users/"));

	}

	@Test
	void editUser() throws Exception {
		User existingUser = new User();
		existingUser.setId(1L);
		existingUser.setName("Existing User");
		existingUser.setEmail("123@gmail.com");
		existingUser.setPassword("123456789");

		when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

		// When & Then
		mockMvc.perform(get("/admin/users/edit/1")).andExpect(status().isOk())
				.andExpect(view().name("admin/users/edit")).andExpect(model().attributeExists("user"));
	}

	@Test
	void updateUser() throws Exception {
		// Given
		User existingUser = new User();
		existingUser.setId(1L);
		existingUser.setName("Existing User");
		existingUser.setEmail("123@gmail.com");
		existingUser.setPassword("123456789");

		when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

		// When
		mockMvc.perform(post("/admin/users/edit").param("id", "1").param("name", "riya")
				.param("email", "riya@gmail.com").param("password", "123456789")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin/users/"));
	}

	@Test
	void deleteUser() throws Exception {
		// Given
		User existingUser = new User();
		existingUser.setId(1L);
		when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

		// When
		mockMvc.perform(get("/admin/users/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin/users/"));

		// Verify
		verify(userRepository).delete(existingUser);
	}

	@Test
	void deleteNonExistingUser() throws Exception {
		// Given
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		// When
		mockMvc.perform(get("/admin/users/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin/users/"));

		// Verify
		verify(userRepository, never()).delete(any());
	}
}

// Verify
//		verify(userRepository).save(existingUser);
//		assertEquals("Riya", existingUser.getName());
