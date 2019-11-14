package com.dusan.forum.service;

import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.annotation.Secured;

import com.dusan.forum.request.TopicRequest;
import com.dusan.forum.response.TopicResponse;

public interface TopicService {

	@Secured({ "ROLE_MEMBER" })
	void createTopic(long forumId, TopicRequest createTopicRequest);

	@Secured({ "ROLE_ADMIN" })
	void deleteTopic(long topicId);

	TopicResponse getTopic(long topicId);

	PagedModel<TopicResponse> getForumTopics(long forumId, int page, int limit);

	PagedModel<TopicResponse> getTopics(int page, int limit);

	PagedModel<TopicResponse> getUserTopics(long userId, int page, int limit);
}
