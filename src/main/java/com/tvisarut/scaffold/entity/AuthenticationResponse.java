package com.tvisarut.scaffold.entity;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
	private final String accessToken; //uneditable variable with final
	public AuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}
    public String getAccessToken() {
        return accessToken;
    }
}
