package com.dusan.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/forums")
public class ForumController {

	@Autowired
	private ForumService forumService;

	// create new forum
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createForum(@Valid @RequestBody ForumRequest createForumRequest) {
		forumService.createForum(createForumRequest);
	}

	// get all or root forums
	@GetMapping
	public PagedModel<ForumResponse> getForums(
			@RequestParam(value = "root", defaultValue = "false") boolean root,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		if (root)
			return forumService.getRootForums(page, limit);
		else
			return forumService.getAllForums(page, limit);
	}

	// get specific forum
	@GetMapping("/{forumId}")
	public ForumResponse getForum(@PathVariable long forumId) {
		return forumService.getForum(forumId);
	}

	// get sub forums of specific forum
	@GetMapping("/{forumId}/forums")
	public PagedModel<ForumResponse> getSubForums(
			@PathVariable long forumId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		return forumService.getSubForums(forumId, page, limit);
	}

}
