package com.tvisarut.scaffold.exception;

public class EmployeeNotFound extends RuntimeException{
	public EmployeeNotFound(long id) {
		super("Could not find the employee "+id);
	}
}
