package com.dusan.forum.response;

import java.util.HashSet;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

public class UserResponse extends RepresentationModel<UserResponse> {

	private Long id;
	private String username;
	private String email;
	private Set<String> roles;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getRole() {
		return roles;
	}

	public void setRole(Set<String> role) {
		this.roles = role;
	}

	public void addRole(String role) {
		if (roles == null)
			roles = new HashSet<>();
		roles.add(role);
	}
}
