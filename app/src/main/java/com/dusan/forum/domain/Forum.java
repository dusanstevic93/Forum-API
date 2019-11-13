package com.dusan.forum.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Forum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	private Forum parentForum;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Forum getParentForum() {
		return parentForum;
	}

	public void setParentForum(Forum parentForum) {
		this.parentForum = parentForum;
	}

}
