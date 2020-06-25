package com.tvisarut.scaffold;

public class EmployeeNotFoundException extends RuntimeException{
	public EmployeeNotFoundException(long id) {
		super("Could not find the employee "+id);
	}
}
