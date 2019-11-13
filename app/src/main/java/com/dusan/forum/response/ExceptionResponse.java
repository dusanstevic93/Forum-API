package com.dusan.forum.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {

	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	
	public ExceptionResponse(HttpStatus status, String message) {
		this.timestamp = LocalDateTime.now();
		this.status = status.value();
		this.error = status.name();
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}
	
	
}
