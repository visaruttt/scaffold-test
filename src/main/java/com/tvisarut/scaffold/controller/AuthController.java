package com.tvisarut.scaffold.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tvisarut.scaffold.entity.AuthenticationRequest;
import com.tvisarut.scaffold.entity.AuthenticationResponse;
import com.tvisarut.scaffold.exception.ScaffoldServiceException;
import com.tvisarut.scaffold.service.AuthService;
import com.tvisarut.scaffold.util.JWTUtil;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthService authUser;
	
	@Autowired
	private JWTUtil jwtTokenUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/login")
	public ResponseEntity<?> Login(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		System.out.println("username is "+authenticationRequest.getUsername());
		System.out.println("password is "+authenticationRequest.getPassword());
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), passwordEncoder.encode(authenticationRequest.getPassword())));
			System.out.println("trying "+authenticationRequest.getUsername());

		} catch (BadCredentialsException e) {
			System.out.println("auth incorrect "+e);
			throw new ScaffoldServiceException("Incorrect username or password", 403);
		}
		
		final UserDetails userDetails = authUser.loadUserByUsername(authenticationRequest.getUsername());
		final String accessToken = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(accessToken));
	}
}
