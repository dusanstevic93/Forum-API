package com.dusan.forum.request;

import javax.validation.constraints.NotBlank;

public class PostRequest {

	@NotBlank(message = "text is required")
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
