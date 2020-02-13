package com.dusan.forum.service;

import org.springframework.hateoas.PagedModel;

import com.dusan.forum.request.ForumRequest;
import com.dusan.forum.response.ForumResponse;

public interface ForumService {

	void createForum(long parentId, ForumRequest createForumRequest);
	
	PagedModel<ForumResponse> getAllForums(int page, int limit);
	
	PagedModel<ForumResponse> getRootForums(int page, int limit);
	
	PagedModel<ForumResponse> getSubForums(long forumId, int page, int limit);
	
	ForumResponse getForum(long forumId);
}
