package com.tvisarut.scaffold;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmployeeExceptionAdvice {
	@ResponseBody
	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String EmployeeNotFound(EmployeeNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(UnAuthenException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	String UnAuthen(UnAuthenException ex) {
		return ex.getMessage();
	}
}
