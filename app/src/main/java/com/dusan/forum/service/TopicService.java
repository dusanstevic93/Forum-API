package com.dusan.forum.service;

import org.springframework.hateoas.PagedModel;

import com.dusan.forum.request.TopicRequest;
import com.dusan.forum.response.TopicResponse;

public interface TopicService {

	void createTopic(long forumId, TopicRequest createTopicRequest);
	void deleteTopic(long topicId);
	void deleteUserTopic(long userId, long topicId);
	TopicResponse getTopic(long topicId);
	PagedModel<TopicResponse> getForumTopics(long forumId, int page, int limit);
	PagedModel<TopicResponse> getTopics(int page, int limit);
	PagedModel<TopicResponse> getUserTopics(long userId, int page, int limit);
}
