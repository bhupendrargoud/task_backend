package com.gavika.payroll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gavika.payroll.model.Employee;

public interface EmployeeRepository  extends JpaRepository<Employee , Integer>{
	Optional<Employee>  findByemployeeId(String employeeId);

	Optional<Employee> findTopByOrderByIdDesc();
	 
}