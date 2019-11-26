package com.dusan.forum.request;

import javax.validation.constraints.NotBlank;

public class ForumRequest {

	@NotBlank(message = "forum name is required")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
