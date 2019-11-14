package com.dusan.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
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

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	// create new post on specific topic
	@PostMapping("/topics/{topicId}/posts")
	@ResponseStatus(HttpStatus.CREATED)
	public void createPost(@PathVariable long topicId, @Valid @RequestBody PostRequest createPostRequest) {
		postService.createPost(topicId, createPostRequest);
	}

	// get single post
	@GetMapping("/posts/{postId}")
	public PostResponse getPost(@PathVariable long postId) {
		return postService.getPost(postId);
	}
	
	// get sub posts
	@GetMapping("/posts/{postId}/posts")
	public PagedModel<PostResponse> getSubPosts(
			@PathVariable long postId, 
			@RequestParam(value = "page", defaultValue = "1") int page, 
			@RequestParam(value = "limit", defaultValue="10") int limit){
		return postService.getSubPosts(postId, page, limit);
	}
	
	// get topic posts
	@GetMapping("/topics/{topicId}/posts")
	public PagedModel<PostResponse> getTopicPosts(
			@PathVariable long topicId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		return postService.getTopicPosts(topicId, page, limit);
	}

	// get user posts
	@GetMapping("/users/{userId}/posts")
	public PagedModel<PostResponse> getUserPosts(
			@PathVariable long userId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit){
		return postService.getUserPosts(userId, page, limit);
	}
	
	// edit post
	@PutMapping("/posts/{postId}")
	public void editPost(@PathVariable long userId, @PathVariable long postId, 
			@Valid @RequestBody PostRequest editPostRequest) {
		postService.editPost(postId, editPostRequest);
	}

	// delete single post
	@DeleteMapping("/posts/{postId}")
	public void deletePost(@PathVariable long postId) {
		postService.deletePost(postId);
	}
}
