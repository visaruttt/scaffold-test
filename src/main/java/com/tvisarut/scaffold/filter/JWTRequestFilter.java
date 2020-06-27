package com.tvisarut.scaffold.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.tvisarut.scaffold.service.AuthService;
import com.tvisarut.scaffold.util.JWTUtil;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
	@Autowired
	private AuthService authUser;

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// Middleware function
		final String authorizationHeader = request.getHeader("Authorization"); // detach header peek the Authorization

		String userName = null;
		String accessToken = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			// get the token on the header and substring the bearer off
			accessToken = authorizationHeader.substring(7);
			userName = jwtUtil.extractUsername(accessToken); // get the username in the JWT token
		}

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.authUser.loadUserByUsername(userName);
			if (jwtUtil.validateToken(accessToken, userDetails)) {
				// if token valid, success login
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				// set the detail to webauth detail source to context by setAuth function
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}
