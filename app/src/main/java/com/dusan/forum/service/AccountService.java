package com.dusan.forum.service;

import com.dusan.forum.domain.User;

public interface AccountService {

	void activate(String token);

	void sendActivationMail(User user);
	
	void resendActivationMail(String token);
}
