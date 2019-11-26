package com.dusan.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.dusan.forum.swagger.OperationDescription;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User")
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Operation(summary = "Create user", description = OperationDescription.CRATE_USER)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewUser(@Valid @RequestBody UserRequest userRequest) {
		userService.createUser(userRequest);
	}
	
	@Operation(summary = "Get user", description = OperationDescription.GET_USER)
	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserResponse getUser(@PathVariable long userId) {
		return userService.getUser(userId);
	}
	
	@Operation(summary = "Get users", description = OperationDescription.GET_USERS)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<UserResponse> getUsers(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit){
		return userService.getUsers(page, limit);
	}
}
