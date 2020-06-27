package com.tvisarut.scaffold.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tvisarut.scaffold.entity.ResponseMessage;

@ControllerAdvice
public class ExceptionAdvice {

	@ResponseBody
	@ExceptionHandler(ScaffoldServiceException.class)
	public ResponseEntity<ResponseMessage> mapException(ScaffoldServiceException ex) {
		ResponseMessage error = new ResponseMessage(ex.getStatusCode(), ex.getMessage());
		return new ResponseEntity<ResponseMessage>(error, HttpStatus.valueOf(ex.getStatusCode()));
	}
}
