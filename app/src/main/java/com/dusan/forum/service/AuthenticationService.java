package com.dusan.forum.service;

import com.dusan.forum.request.AuthenticationRequest;

public interface AuthenticationService {

	String authenticate(AuthenticationRequest request);
}
