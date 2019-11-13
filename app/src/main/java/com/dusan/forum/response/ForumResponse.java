package com.dusan.forum.response;

import org.springframework.hateoas.RepresentationModel;

public class ForumResponse extends RepresentationModel<ForumResponse> {

	private long id;
	private long parentId;
	private String name;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}
