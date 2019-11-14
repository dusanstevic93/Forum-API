package com.dusan.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dusan.forum.request.AuthenticationRequest;
import com.dusan.forum.response.AuthenticationResponse;
import com.dusan.forum.service.AuthenticationService;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authService;

	@PostMapping
	public AuthenticationResponse authentication(@Valid @RequestBody AuthenticationRequest request) {
		String token= authService.authenticate(request);
		return new AuthenticationResponse(token);
	}

}
