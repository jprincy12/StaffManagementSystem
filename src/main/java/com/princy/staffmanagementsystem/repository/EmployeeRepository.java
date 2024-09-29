package com.princy.staffmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.princy.staffmanagementsystem.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
