package com.dusan.forum.service;

import org.springframework.hateoas.PagedModel;

import com.dusan.forum.request.UserRequest;
import com.dusan.forum.response.UserResponse;

public interface UserService {

	void createUser(UserRequest userRequest);
	
	UserResponse getUser(long userId);
	
	PagedModel<UserResponse> getUsers(int page, int limit);
}
