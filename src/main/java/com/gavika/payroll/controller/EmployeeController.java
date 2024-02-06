package com.gavika.payroll.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gavika.payroll.model.Employee;
import com.gavika.payroll.repository.EmployeeRepository;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	 @Autowired
	 EmployeeRepository employeeRepository;

	    @GetMapping("/all")
	    public ResponseEntity<List<Employee>> getAllEmployees() {
	        List<Employee> employees = new ArrayList<>();
	        employeeRepository.findAll().forEach(employees::add);
	        return new ResponseEntity<>(employees, HttpStatus.OK);
	    }

	    @PostMapping("/save")
	    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
	        employee.setPassword("password");
	        employee.setEmployeeId(newid(employee.getEmployeeId()));
	        Employee savedEmployee = employeeRepository.save(employee);
	        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
	    }
	    
	    @GetMapping("/search/{employeeId}")
	    public ResponseEntity<Employee> getEmployeeById(@PathVariable int employeeId){
	    	
	        Optional<Employee> employee = employeeRepository.findById(employeeId);
	        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
	                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }
	    
	    @GetMapping("/search/eid/{employeeId}")
	    public ResponseEntity<Employee> getEmployeeByeId(@PathVariable String employeeId){
	    	
	    	 Optional<Employee> employee = employeeRepository.findByemployeeId(employeeId);
		        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
		                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }
	    
	    @GetMapping("/login/{employeeId}/{password}")
	    public ResponseEntity<String> authEmployee(@PathVariable String employeeId, @PathVariable String password) {
	    	 Optional<Employee> employeeOptional = employeeRepository.findByemployeeId(employeeId);

	        if (employeeOptional.isPresent()) {
	            Employee employee = employeeOptional.get();
	            
	            // Check if the provided password matches the stored password
	            if (employee.getPassword().equals(password)) {
	                // Authentication successful
	            	String jsonResponse = "{\"employeeId\":\"" + employee.getEmployeeId() + "\"}";

	            	return ResponseEntity.ok(jsonResponse);
	            } else {
	                // Incorrect password
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
	            }
	        } else {
	            // Employee not found
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
	        }
	    }

	    
	    
	    @DeleteMapping("/delete/{Id}")
	    public ResponseEntity<Void> deleteEmployee(@PathVariable int Id) {
	    	
	        if (employeeRepository.existsById(Id) && Id!=1) {
	        	
	            employeeRepository.deleteById(Id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    
	 // Update for Password
	    @PatchMapping("/update_password/{employeeId}")
	    public ResponseEntity<Employee> updateEmployeePassword(@PathVariable String employeeId, @RequestBody Employee partialEmployee) {
	        return updateEmployeeField(employeeId, partialEmployee, "password");
	    }

	    // Update for Address
	    @PatchMapping("/update_address/{employeeId}")
	    public ResponseEntity<Employee> updateEmployeeAddress(@PathVariable String employeeId, @RequestBody Employee partialEmployee) {
	        return updateEmployeeField(employeeId, partialEmployee, "address");
	    }

	    
	    private ResponseEntity<Employee> updateEmployeeField(String employeeId, Employee partialEmployee, String fieldName) {
	    	int Id = extractNum(employeeId);
	        Optional<Employee> existingEmployee = employeeRepository.findById(Id);

	        if (existingEmployee.isPresent()) {
	            Employee employee = existingEmployee.get();

	            
	            switch (fieldName) {
	                case "password":
	                    if (partialEmployee.getPassword() != null) {
	                        employee.setPassword(partialEmployee.getPassword());
	                    }
	                    break;
	                case "address":
	                    if (partialEmployee.getAddress() != null) {
	                        employee.setAddress(partialEmployee.getAddress());
	                    }
	                    break;
	                

	                default:
	                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
	            }

	            Employee savedEmployee = employeeRepository.save(employee);
	            return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    
	    
	    private int extractNum(String input) {
	        Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(input);
	            return Integer.parseInt(matcher.group());
	    }
	    
	    private String newid(String name) {
	    	 Optional<Employee> lastEmployee = employeeRepository.findTopByOrderByIdDesc();
	         int lId= lastEmployee.map(Employee::getId).orElse(null)+1;
	         name+="-"+lId;
			return name;
			
	    	
	    }
	    
	   

   
}

