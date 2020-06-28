package com.tvisarut.scaffold.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tvisarut.scaffold.entity.Employee;
import com.tvisarut.scaffold.exception.ScaffoldServiceException;
import com.tvisarut.scaffold.repository.EmployeeRepository;

@Service
@ComponentScan("com.tvisarut.scaffold.repository")
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;

	public Employee saveEmployee(Employee employee) {
		if (repository.findByUsername(employee.getUsername())!=null) { //username already exist
			throw new ScaffoldServiceException("username "+employee.getUsername()+" already exist", 400);
		}
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		return repository.save(employee);
	}

	public List<Employee> getEmployees() {
		return repository.findAll();
	}

	public Employee getEmployeeById(Long id) {
		Employee employee = repository.findById(id).orElse(null);
		if (employee != null) {
			return employee;
		}
		throw new ScaffoldServiceException("employee id "+id+" not found", 404);
	}
	public String deleteEmployee(Long id) {
		if (repository.findById(id) != null) {
			repository.deleteById(id);
			return "employee id "+id+" removed";
		}
		throw new ScaffoldServiceException("employee id "+id+" not found", 404);
	}

	public Employee updateEmployee(Long id, Employee employee) {
		Employee existEmployee = repository.findById(id).orElse(null);
		if (existEmployee != null) {
			if(employee.getFirstname()!=null) {
				existEmployee.setFirstname(employee.getFirstname());	
			}
			if(employee.getLastname()!=null) {
				existEmployee.setLastname(employee.getLastname());	
			}
			return repository.save(existEmployee);
		}
		throw new ScaffoldServiceException("employee id "+id+" not found", 404);
	}
}
