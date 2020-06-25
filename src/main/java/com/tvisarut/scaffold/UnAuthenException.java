package com.tvisarut.scaffold;

public class UnAuthenException extends RuntimeException {

	public UnAuthenException() {
		super("Token Invalid");
	}
}
