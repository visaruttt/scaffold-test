package com.tvisarut.scaffold.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.tvisarut.scaffold.entity.Employee;
import com.tvisarut.scaffold.exception.EmployeeNotFound;
import com.tvisarut.scaffold.repository.EmployeeRepository;

@Service
@ComponentScan("com.tvisarut.scaffold.repository")
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	public Employee saveEmployee(Employee employee) {
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
		throw new EmployeeNotFound(id);
	}
	public String deleteEmployee(Long id) {
		if (getEmployeeById(id) != null) {
			repository.deleteById(id);
			return "employee removed !! " + id;
		}
		throw new EmployeeNotFound(id);
	}

	public Employee updateEmployee(Long id, Employee employee) {
		Employee existEmployee = repository.findById(id).orElse(null);
		if (existEmployee != null) {
			existEmployee.setFirstname(employee.getFirstname());
			existEmployee.setLastname(employee.getLastname());
			return repository.save(existEmployee);
		}
		throw new EmployeeNotFound(id);
	}
}
