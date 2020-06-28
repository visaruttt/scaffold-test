package com.tvisarut.scaffold.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.tvisarut.scaffold.entity.AuthenticationRequest;
import com.tvisarut.scaffold.service.AuthService;
import com.tvisarut.scaffold.util.JWTUtil;

class AuthControllerTest {
	//prepare for integration test
	@Autowired
	private AuthenticationRequest mockAuthRequest;

	@Autowired
	private AuthService authUser;

	@Autowired
	private JWTUtil jwtTokenUtil;

	@Test
	void test() {
		fail("not implement yet");
	}
}
