package com.tvisarut.scaffold;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

@RestController // import library annotation
public class EmployeeController {

	// list for return the model || using java.util.list
	private List<EmployeeModel> modelEmployees = new ArrayList<>();
	private final AtomicLong counter = new AtomicLong(1);

	// constructor class
	public EmployeeController() {
//		modelEmployees.add(new EmployeeModel(1,"visarut"));
//		modelEmployees.add(new EmployeeModel(2,"employee2"));
//		modelEmployees.add(new EmployeeModel(3,"employee3"));
	}

	// GET all employees
	@GetMapping("/employee") // command + shift + o for import library on MacOS
	public List<EmployeeModel> getEmployee(@RequestParam(defaultValue = "0") long id) {
		if (id != 0) {
			// if parameter is detected
			List<EmployeeModel> employee = new ArrayList<>();
			EmployeeModel resultEmployee = modelEmployees.stream().filter(result -> result.getId() == id).findFirst()
					.orElseThrow(() -> new EmployeeNotFoundException(id));

			// convert as list to using the same return format
			employee.add(resultEmployee);
			return employee;
		}
		return modelEmployees; // return the result with JSON model
	}

	// GET employee by id
	@GetMapping("/employee/{id}")
	public EmployeeModel getEmployeeById(@PathVariable int id) {
		return modelEmployees.stream().filter(result -> result.getId() == id).findFirst()
				.orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	// POST to create the employee
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/employee")
	public EmployeeModel createEmployee(@RequestBody EmployeeModel empRegistering) {
		long index = counter.getAndIncrement();
		modelEmployees.add(new EmployeeModel(index, empRegistering.getName()));
		return modelEmployees.get((int) index - 1);
	}

	// PUT to update the employee
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/employee/{id}")
	public EmployeeModel updateEmployee(@RequestBody EmployeeModel empEditing, @PathVariable long id) {
		modelEmployees.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
			result.setName(empEditing.getName());
		}, () -> {
			throw new EmployeeNotFoundException(id);
		});
		return empEditing;

	}

	// PUT to update the employee
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/employee/{id}")
	public String deleteEmployee(@PathVariable long id) {
		modelEmployees.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
			modelEmployees.remove(result);
		}, () -> {
			throw new EmployeeNotFoundException(id);
		});
		return "removed";

	}
}