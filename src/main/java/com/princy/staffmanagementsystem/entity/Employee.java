package com.princy.staffmanagementsystem.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDate getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDate deletedAt) {
		this.deletedAt = deletedAt;
	}

	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	private String address;
	@Column(nullable=false)
	private LocalDate dateOfBirth;
	
	@Column(nullable = false, updatable = false)
	private LocalDate createdAt;
	@Column(nullable = false)
	private LocalDate updatedAt;
	@Column
	private LocalDate deletedAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDate.now();
		this.updatedAt = LocalDate.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDate.now();
	}
	
}
