package com.dusan.forum.request;

import javax.validation.constraints.NotBlank;

public class TopicRequest {
	
	@NotBlank(message = "topic title is required")
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
