package com.dusan.forum.request;

import javax.validation.constraints.NotBlank;

public class ForumRequest {

	private Long parentId;
	
	@NotBlank(message = "forum name is required")
	private String name;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
