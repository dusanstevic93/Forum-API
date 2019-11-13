package com.dusan.forum.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TopicRequest {
	
	@NotNull(message = "user id is required")
	private Long userId;
	
	@NotBlank(message = "topic title is required")
	private String title;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
