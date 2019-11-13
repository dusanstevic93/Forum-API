package com.dusan.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dusan.forum.request.TopicRequest;
import com.dusan.forum.response.TopicResponse;
import com.dusan.forum.service.TopicService;

@RestController
public class TopicController {

	@Autowired
	private TopicService topicService;

	// create a new topic on specific forum
	@PostMapping("/forums/{forumId}/topics")
	@ResponseStatus(HttpStatus.CREATED)
	public void createTopic(@PathVariable long forumId, @Valid @RequestBody TopicRequest createTopicRequest) {
		topicService.createTopic(forumId, createTopicRequest);
	}

	// get single topic
	@GetMapping("/topics/{topicId}")
	public TopicResponse getTopic(@PathVariable long topicId) {
		return topicService.getTopic(topicId);
	}

	// get topics of specific forum
	@GetMapping("/forums/{forumId}/topics")
	public PagedModel<TopicResponse> getForumTopics(
			@PathVariable long forumId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		return topicService.getForumTopics(forumId, page, limit);
	}

	// get topics of specific user
	@GetMapping("/users/{userId}/topics")
	public PagedModel<TopicResponse> getUserTopics(
			@PathVariable long userId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		return topicService.getUserTopics(userId, page, limit);
	}

	// delete single topic
	@DeleteMapping("/topics/{topicId}")
	public void deleteTopic(@PathVariable long topicId) {
		topicService.deleteTopic(topicId);
	}

	// delete topic of specific user
	@DeleteMapping("/users/{userId}/topics/{topicId}")
	public void deleteUserTopic(@PathVariable long userId, long topicId) {
		topicService.deleteUserTopic(userId, topicId);
	}
}
