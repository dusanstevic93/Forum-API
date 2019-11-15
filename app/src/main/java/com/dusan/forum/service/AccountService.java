package com.dusan.forum.service;

import com.dusan.forum.domain.User;
import com.dusan.forum.response.AccountActivationResponse;

public interface AccountService {

	AccountActivationResponse activate(String token);

	void sendActivationMail(User user);
	
	void resendActivationMail(String token);
}
