package com.dusan.forum.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dusan.forum.request.ForumRequest;
import com.dusan.forum.response.ForumResponse;
import com.dusan.forum.service.ForumService;
import com.dusan.forum.swagger.OperationDescription;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Forum")
@RestController
@RequestMapping("/forums")
public class ForumController {

	@Autowired
	private ForumService forumService;

	@Operation(summary = "Create forum",
			description = OperationDescription.CREATE_FORUM,
			security = @SecurityRequirement(name = "JWTAuth"))
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Secured({ "ROLE_ADMIN" })
	public void createForum(
			@Valid @RequestBody ForumRequest createForumRequest,
			@RequestParam(value = "parentId", defaultValue = "0") long parentId) {
		forumService.createForum(parentId, createForumRequest);
	}

	@Operation(summary = "Get forums", description = OperationDescription.GET_FORUMS)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<ForumResponse> getForums(
			@RequestParam(value = "root", defaultValue = "false") boolean root,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		if (root)
			return forumService.getRootForums(page, limit);
		else
			return forumService.getAllForums(page, limit);
	}

	@Operation(summary = "Get forum", description = OperationDescription.GET_FORUM)
	@GetMapping(value = "/{forumId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ForumResponse getForum(@PathVariable long forumId) {
		return forumService.getForum(forumId);
	}

	@Operation(summary = "Get sub forums", description = OperationDescription.GET_SUB_FORUMS)
	@GetMapping(value = "/{forumId}/forums", produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<ForumResponse> getSubForums(
			@PathVariable long forumId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		return forumService.getSubForums(forumId, page, limit);
	}

}
