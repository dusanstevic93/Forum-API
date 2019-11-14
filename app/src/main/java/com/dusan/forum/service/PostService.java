package com.dusan.forum.service;

import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.annotation.Secured;

import com.dusan.forum.request.PostRequest;
import com.dusan.forum.response.PostResponse;

public interface PostService {

	@Secured({ "ROLE_MEMBER" })
	void createPost(long topicId, PostRequest createPostRequest);

	PostResponse getPost(long postId);

	@Secured({ "ROLE_MEMBER" })
	void deletePost(long postId);

	@Secured({ "ROLE_MEMBER" })
	void editPost(long postId, PostRequest editPostRequest);

	PagedModel<PostResponse> getTopicPosts(long topicId, int page, int limit);

	PagedModel<PostResponse> getUserPosts(long userId, int page, int limit);
	
	PagedModel<PostResponse> getSubPosts(long postId, int page, int limit);
}
