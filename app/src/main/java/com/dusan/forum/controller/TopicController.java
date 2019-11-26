package com.dusan.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
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
import com.dusan.forum.swagger.OperationDescription;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Topic")
@RestController
public class TopicController {

	@Autowired
	private TopicService topicService;

	@Operation(summary = "Create new topic", description = OperationDescription.CREATE_TOPIC,
			security = @SecurityRequirement(name = "JWTAuth"))
	@PostMapping(value = "/forums/{forumId}/topics", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void createTopic(
			@PathVariable long forumId, 
			@Valid @RequestBody TopicRequest createTopicRequest,
			@Parameter(hidden = true) Authentication auth) {
		if (auth == null)
			throw new AccessDeniedException("Unauthorized");
		topicService.createTopic(forumId, auth.getName(), createTopicRequest);
	}

	@Operation(summary = "Get topic", description = OperationDescription.GET_TOPIC)
	@GetMapping(value = "/topics/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public TopicResponse getTopic(@PathVariable long topicId) {
		return topicService.getTopic(topicId);
	}

	@Operation(summary = "Get forum topics", description = OperationDescription.GET_FORUM_TOPICS)
	@GetMapping(value = "/forums/{forumId}/topics", produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<TopicResponse> getForumTopics(
			@PathVariable long forumId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		return topicService.getForumTopics(forumId, page, limit);
	}

	@Operation(summary = "Get user topics", description = OperationDescription.GET_USER_TOPICS)
	@GetMapping(value = "/users/{userId}/topics", produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<TopicResponse> getUserTopics(
			@PathVariable long userId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		return topicService.getUserTopics(userId, page, limit);
	}

	@Operation(summary = "Delete topic", description = OperationDescription.DELETE_TOPIC,
			security = @SecurityRequirement(name = "JWTAuth"))
	@DeleteMapping("/topics/{topicId}")
	public void deleteTopic(@PathVariable long topicId) {
		topicService.deleteTopic(topicId);
	}
}
