package com.dusan.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dusan.forum.request.UserRequest;
import com.dusan.forum.response.UserResponse;
import com.dusan.forum.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	// create new user
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewUser(@Valid @RequestBody UserRequest userRequest) {
		userService.createUser(userRequest);
	}
	
	// get specific user
	@GetMapping("/{userId}")
	public UserResponse getUser(@PathVariable long userId) {
		return userService.getUser(userId);
	}
	
	// get all users
	@GetMapping
	public PagedModel<UserResponse> getUsers(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit){
		return userService.getUsers(page, limit);
	}
	
	// activate user account
	@GetMapping("/account-activation")
	public String accountActivation(@RequestParam("token") String token) {
		return userService.activateAccount(token);
	}
	
	
}
