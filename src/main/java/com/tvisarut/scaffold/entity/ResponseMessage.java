package com.tvisarut.scaffold.entity;

public class ResponseMessage {
	private int statusCode;
	private String message;
	public ResponseMessage(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
	public int getstatusCode() {
		return statusCode;
	}
	public void setstatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getmessage() {
		return message;
	}
	public void setmessage(String message) {
		this.message = message;
	}
}
