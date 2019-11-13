package com.dusan.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dusan.forum.request.AuthenticationRequest;
import com.dusan.forum.security.JWTUtills;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

	@Autowired
	private AuthenticationManager authManager;
	
	@Override
	public String authenticate(AuthenticationRequest request) {
		String username = request.getUsername();
		String password = request.getPassword();
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication auth = authManager.authenticate(authToken);
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String token = JWTUtills.createToken(userDetails);
		return "Bearer " + token;
		
	}

}
