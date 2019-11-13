package com.dusan.forum.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dusan.forum.request.AuthenticationRequest;
import com.dusan.forum.service.AuthenticationService;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authService;

	@PostMapping
	public void authentication(@Valid @RequestBody AuthenticationRequest request, HttpServletResponse response) {
		String header = authService.authenticate(request);
		response.setHeader("Authentication", header);
	}

}
