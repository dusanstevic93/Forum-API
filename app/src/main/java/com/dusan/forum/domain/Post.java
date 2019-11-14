package com.dusan.forum.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String text;

	@CreationTimestamp
	private LocalDateTime createdOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_post_id")
	private Post parent;

	@ManyToOne(fetch = FetchType.LAZY)
	private Topic topic;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public Post getParent() {
		return parent;
	}

	public void setParentPost(Post parent) {
		this.parent = parent;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
