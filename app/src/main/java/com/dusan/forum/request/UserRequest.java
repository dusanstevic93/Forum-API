package com.dusan.forum.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class UserRequest {

	@NotBlank(message = "username is required")
	private String username;
	
	@NotBlank(message = "password is required")
	@Length(min = 5, message = "password must contain at least 5 characters")
	private String password;
	
	@NotBlank(message = "email is required")
	@Email(message = "email must be valid")
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
