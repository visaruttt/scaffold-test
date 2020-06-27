package com.tvisarut.scaffold.entity;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
	private final String jwt; //uneditable variable with final
	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}
    public String getJwt() {
        return jwt;
    }
}
