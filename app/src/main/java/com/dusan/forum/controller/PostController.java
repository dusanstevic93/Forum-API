package com.dusan.forum.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dusan.forum.request.PostRequest;
import com.dusan.forum.response.PostResponse;
import com.dusan.forum.service.PostService;
import com.dusan.forum.swagger.OperationDescription;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Post")
@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@Operation(summary = "Create post", description = OperationDescription.CREATE_POST,
			security = @SecurityRequirement(name = "JWTAuth"))
	@PostMapping(value = "/topics/{topicId}/posts", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Secured({ "ROLE_MEMBER" })
	public void createPost(
			@PathVariable long topicId, 
			@RequestParam(value = "parentId", defaultValue = "0") long parentId,
			@Valid @RequestBody PostRequest createPostRequest,
			@Parameter(hidden = true) Authentication auth) {
		postService.createPost(topicId, parentId, auth.getName(), createPostRequest);
	}

	@Operation(summary = "Get post", description = OperationDescription.GET_POST)
	@GetMapping(value = "/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PostResponse getPost(@PathVariable long postId) {
		return postService.getPost(postId);
	}
	
	@Operation(summary = "Get sub posts", description = OperationDescription.GET_SUB_POSTS)
	@GetMapping(value = "/posts/{postId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<PostResponse> getSubPosts(
			@PathVariable long postId, 
			@RequestParam(value = "page", defaultValue = "1") int page, 
			@RequestParam(value = "limit", defaultValue="10") int limit){
		return postService.getSubPosts(postId, page, limit);
	}
	
	@Operation(summary = "Get topic posts", description = OperationDescription.GET_TOPIC_POSTS)
	@GetMapping(value = "/topics/{topicId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<PostResponse> getTopicPosts(
			@PathVariable long topicId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		return postService.getTopicPosts(topicId, page, limit);
	}

	@Operation(summary = "Get user posts", description = OperationDescription.GET_USER_POSTS)
	@GetMapping(value = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<PostResponse> getUserPosts(
			@PathVariable long userId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit){
		return postService.getUserPosts(userId, page, limit);
	}
	
	@Operation(summary = "Edit post", description = OperationDescription.EDIT_POST, 
			security = @SecurityRequirement(name = "JWTAuth"))
	@PutMapping(value = "/posts/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Secured({ "ROLE_MEMBER" })
	public void editPost(
			@PathVariable long postId, 
			@Valid @RequestBody PostRequest editPostRequest,
			@Parameter(hidden = true) Authentication auth) {
		postService.editPost(postId, auth.getName(), editPostRequest);
	}

	@Operation(summary = "Delete post", description = OperationDescription.DELETE_POST,
			security = @SecurityRequirement(name = "JWTAuth"))
	@DeleteMapping(value = "/posts/{postId}")
	@Secured({ "ROLE_ADMIN", "ROLE_MEMBER" })
	public void deletePost(@PathVariable long postId, @Parameter(hidden = true) Authentication auth) {
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
			postService.deleteAnyPost(postId);
		else
			postService.deleteUserPost(auth.getName(), postId);
				
	}
}
