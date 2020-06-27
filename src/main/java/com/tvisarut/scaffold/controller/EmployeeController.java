package com.tvisarut.scaffold.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tvisarut.scaffold.entity.Employee;
import com.tvisarut.scaffold.service.EmployeeService;

@RestController // import library annotation
public class EmployeeController {

	@Autowired
	private EmployeeService empService;
	// constructor class
	public EmployeeController() {
		
	}

	// GET employees with Get All and Get by parameter(id)
	@GetMapping("/employee") // command + shift + o for import library on MacOS
	public List<Employee> getEmployee(@RequestParam(defaultValue = "0") Long id) {
		if (id != 0) {
			//parameter id is detected
			List<Employee> employees = new ArrayList<>();
			employees.add(empService.getEmployeeById(id));
			return employees;
		}
		return empService.getEmployees(); // return the result with JSON model
	}

	// POST to create the employee
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody Employee empRegistering) {
		return empService.saveEmployee(empRegistering);
	}

	// PUT to update the employee
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/employee/{id}")
	public Employee updateEmployee(@RequestBody Employee empEditing, @PathVariable Long id) {
		return empService.updateEmployee(id,empEditing);
	}

	// Delete to update the employee
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/employee/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		return empService.deleteEmployee(id);
	}
}
