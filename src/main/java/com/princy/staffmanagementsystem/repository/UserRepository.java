package com.princy.staffmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.princy.staffmanagementsystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findByEmail(String username);

}
