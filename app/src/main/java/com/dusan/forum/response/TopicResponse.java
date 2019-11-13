package com.dusan.forum.response;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

public class TopicResponse extends RepresentationModel<TopicResponse> {

	private long id;
	private String title;
	private LocalDateTime createdOn;
	private long userId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
