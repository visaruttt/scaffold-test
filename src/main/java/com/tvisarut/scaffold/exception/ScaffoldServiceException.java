package com.tvisarut.scaffold.exception;

//lombok not working....
public class ScaffoldServiceException extends RuntimeException{
	private int statusCode;
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public ScaffoldServiceException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}
}
