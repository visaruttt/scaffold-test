package com.tvisarut.scaffold.exception;

public class UnAuthenException extends RuntimeException {

	public UnAuthenException() {
		super("Token Invalid");
	}
}
