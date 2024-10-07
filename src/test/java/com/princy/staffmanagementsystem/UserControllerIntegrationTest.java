package com.princy.staffmanagementsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.princy.staffmanagementsystem.entity.User;
import com.princy.staffmanagementsystem.enums.Role;
import com.princy.staffmanagementsystem.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		User user = new User();
		user.setName("Admin User");
		user.setEmail("admin@example.com");
		user.setPassword("password123"); // You may want to encode this password in real scenario
		user.setUsername("admin");
		user.setRole(Role.ADMIN);
		userRepository.save(user);
	}

	@AfterEach
	public void removeUsers() {
		userRepository.deleteAll();
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testListUsers() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/")).andReturn();

		assertEquals(200, result.getResponse().getStatus());
		List<User> userList = (List<User>) result.getModelAndView().getModel().get("users");
		assertNotNull(userList);
		assertEquals(1, userList.size());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testAddUser() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/users/create").param("name", "New User")
				.param("email", "newuser@example.com").param("password", "newpassword").param("username", "newuser"))
				.andReturn();
		assertEquals(302, result.getResponse().getStatus());
		assertEquals("redirect:/admin/users/", result.getModelAndView().getViewName());
		List<User> userList = userRepository.findAll();
		assertEquals(2, userList.size());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testEditUser() throws Exception {
		User userToEdit = new User();
		userToEdit.setName("Edit User");
		userToEdit.setEmail("edituser@example.com");
		userToEdit.setPassword("password123");
		userToEdit.setUsername("edituser");
		userToEdit.setRole(Role.ADMIN);
		User user = userRepository.save(userToEdit);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/users/edit")
				.param("id", user.getId().toString()).param("name", "Updated User")
				.param("email", "updateduser@example.com").param("username", "updateduser")).andReturn();

		assertEquals(302, result.getResponse().getStatus());
		assertEquals("redirect:/admin/users/", result.getModelAndView().getViewName());

		User updatedUser = userRepository.findById(userToEdit.getId()).orElse(null);
		assertNotNull(updatedUser);
		assertEquals("Updated User", updatedUser.getName());
		assertEquals("updateduser@example.com", updatedUser.getEmail());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testDeleteUser() throws Exception {

		User userToDelete = new User();
		userToDelete.setName("Delete User");
		userToDelete.setEmail("deleteuser@example.com");
		userToDelete.setPassword("password123");
		userToDelete.setUsername("deleteuser");
		userToDelete.setRole(Role.ADMIN);
		userRepository.save(userToDelete);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/delete/" + userToDelete.getId()))
				.andReturn();

		assertEquals(302, result.getResponse().getStatus());
		assertEquals("redirect:/admin/users/", result.getModelAndView().getViewName());

		List<User> userList = userRepository.findAll();
		assertEquals(1, userList.size());
	}
}