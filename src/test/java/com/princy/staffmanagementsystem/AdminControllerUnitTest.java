//package com.princy.staffmanagementsystem.controller;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.princy.staffmanagementsystem.entity.Employee;
//import com.princy.staffmanagementsystem.repository.EmployeeRepository;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@WithMockUser(username = "test@gmail.com", password = "test@123", roles = { "ADMIN" })
//public class AdminControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private EmployeeRepository employeeRepository;
//
//	@BeforeEach
//	public void setup() {
//		MockitoAnnotations.openMocks(this);
//	}
//
//	@Test
//	public void testDashboard() throws Exception {
//		mockMvc.perform(get("/admin/")).andExpect(status().isOk()).andExpect(view().name("admin/index"))
//				.andExpect(model().attribute("title", "Admin"));
//	}
//
//	@Test
//	public void testListEmployees() throws Exception {
//		// Mock the repository to return a list of employees
//		Employee emp1 = new Employee();
//		Employee emp2 = new Employee();
//		List<Employee> employees = Arrays.asList(emp1, emp2);
//
//		when(employeeRepository.findAll()).thenReturn(employees);
//
//		mockMvc.perform(get("/admin/employee")).andExpect(status().isOk())
//				.andExpect(view().name("admin/employee/index")).andExpect(model().attributeExists("employees"))
//				.andExpect(model().attribute("employees", employees));
//	}
//}
