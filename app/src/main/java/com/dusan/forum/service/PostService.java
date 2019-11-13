package com.dusan.forum.service;

import org.springframework.hateoas.PagedModel;

import com.dusan.forum.request.PostRequest;
import com.dusan.forum.response.PostResponse;

public interface PostService {

	void createPost(long topicId, PostRequest createPostRequest);
	PostResponse getPost(long postId);
	void deletePost(long postId);
	void deleteUserPost(long userId, long postId);
	void editPost(long postId, PostRequest editPostRequest);
	PagedModel<PostResponse> getTopicPosts(long topicId, int page, int limit);
	PagedModel<PostResponse> getUserPosts(long userId, int page, int limit);
}
