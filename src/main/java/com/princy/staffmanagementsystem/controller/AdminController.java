package com.princy.staffmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.princy.staffmanagementsystem.entity.Employee;
import com.princy.staffmanagementsystem.repository.EmployeeRepository;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/")
	public String dashboard(Model model) {
		model.addAttribute("title", "Admin");
		return "admin/index";
	}

	@GetMapping("/employee")
	public String listStudents(Model model) {
		List<Employee> employees = employeeRepository.findAll();
		model.addAttribute("employees", employees);
		return "admin/employee/index";
	}

}