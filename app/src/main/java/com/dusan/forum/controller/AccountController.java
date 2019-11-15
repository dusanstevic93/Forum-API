package com.dusan.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dusan.forum.response.AccountActivationResponse;
import com.dusan.forum.service.AccountService;

@RestController
@RequestMapping("/activation")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	// activate user account
	@GetMapping
	public AccountActivationResponse accountActivation(@RequestParam("token") String token) {
		return accountService.activate(token);
	}
	
	// resend activation link
	@GetMapping("/resend")
	public void resendLink(@RequestParam("token") String token) {
		accountService.resendActivationMail(token);
	}
}
