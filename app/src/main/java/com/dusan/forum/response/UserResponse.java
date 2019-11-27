package com.dusan.forum.response;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

public class UserResponse extends RepresentationModel<UserResponse> {

	private Long id;
	private String username;
	private String email;
	private LocalDateTime createdOn;
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

	public Set<String> getRoles() {
		return roles;
	}
	
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public void setRoles(Set<String> role) {
		this.roles = role;
	}

	public void addRole(String role) {
		if (roles == null)
			roles = new HashSet<>();
		roles.add(role);
	}
}
