package com.princy.staffmanagementsystem.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.princy.staffmanagementsystem.entity.Employee;
import com.princy.staffmanagementsystem.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Transactional
	public void softDelete(Long id) {
		Optional<Employee> optionalStudent = employeeRepository.findById(id);
		if(optionalStudent.isPresent()) {
			Employee employee = optionalStudent.get();
			employee.setDeletedAt(LocalDate.now());
			employeeRepository.save(employee);
		}
	}
}
