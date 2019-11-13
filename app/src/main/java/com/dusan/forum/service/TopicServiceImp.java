package com.dusan.forum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.stereotype.Service;

import com.dusan.forum.controller.PostController;
import com.dusan.forum.controller.TopicController;
import com.dusan.forum.controller.UserController;
import com.dusan.forum.dao.ForumRepository;
import com.dusan.forum.dao.TopicRepository;
import com.dusan.forum.dao.UserRepository;
import com.dusan.forum.domain.Forum;
import com.dusan.forum.domain.Topic;
import com.dusan.forum.domain.User;
import com.dusan.forum.exception.ForumNotFoundException;
import com.dusan.forum.exception.TopicNotFoundException;
import com.dusan.forum.exception.UserNotFoundException;
import com.dusan.forum.request.TopicRequest;
import com.dusan.forum.response.TopicResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class TopicServiceImp implements TopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ForumRepository forumRepository;
	
	@Override
	public PagedModel<TopicResponse> getTopics(int page, int limit) {
		Pageable pageable = PageRequest.of(page - 1, limit);
		Page<Topic> topicPage = topicRepository.findAll(pageable);
		return createResponsePage(topicPage);
	}

	@Override
	public void createTopic(long forumId, TopicRequest createTopicRequest) {
		Topic topic = new Topic();
		Optional<User> optUser = userRepository.findById(createTopicRequest.getUserId());
		if(!optUser.isPresent())
			throw new UserNotFoundException();
		Optional<Forum> optForum = forumRepository.findById(forumId);
		if (!optForum.isPresent())
			throw new ForumNotFoundException();
		topic.setForum(optForum.get());
		topic.setUser(optUser.get());
		topic.setTitle(createTopicRequest.getTitle());
		topicRepository.save(topic);
	}

	@Override
	public TopicResponse getTopic(long topicId) {
		Optional<Topic> optTopic = topicRepository.findById(topicId);
		if (!optTopic.isPresent())
			throw new TopicNotFoundException();
		Topic topic = optTopic.get();
		TopicResponse response = createResponse(topic);
		return response;
	}

	@Override
	public void deleteTopic(long topicId) {
		Optional<Topic> optTopic = topicRepository.findById(topicId);
		if (!optTopic.isPresent())
			throw new TopicNotFoundException();
		topicRepository.delete(optTopic.get());
		
	}

	@Override
	public void deleteUserTopic(long userId, long topicId) {
		Optional<User> optUser = userRepository.findById(userId);
		if (!optUser.isPresent())
			throw new UserNotFoundException();
		Optional<Topic> optTopic = topicRepository.findById(topicId);
		if (!optTopic.isPresent())
			throw new TopicNotFoundException();
		topicRepository.deleteByUserIdAndId(userId, topicId);
		
	}

	@Override
	public PagedModel<TopicResponse> getForumTopics(long forumId, int page, int limit) {
		Optional<Forum> optForum = forumRepository.findById(forumId);
		if (!optForum.isPresent())
			throw new ForumNotFoundException();
		Pageable pageable = PageRequest.of(page - 1, limit);
		Page<Topic> topicPage = topicRepository.findAllByForumId(forumId, pageable);
		return createResponsePage(topicPage);
	}
	
	@Override
	public PagedModel<TopicResponse> getUserTopics(long userId, int page, int limit) {
		Optional<User> optUser = userRepository.findById(userId);
		if (!optUser.isPresent())
			throw new UserNotFoundException();
		Pageable pageable = PageRequest.of(page - 1, limit);
		Page<Topic> topicPage = topicRepository.findAllByUserId(userId, pageable);
		return createResponsePage(topicPage);
	}

	private TopicResponse createResponse(Topic topic) {
		TopicResponse response = new TopicResponse();
		response.setId(topic.getId());
		response.setCreatedOn(topic.getCreatedOn());
		response.setTitle(topic.getTitle());
		response.setUserId(topic.getUser().getId());
		createLinks(response);
		return response;
	}
	
	private PagedModel<TopicResponse> createResponsePage(Page<Topic> topicPage){
		int size = topicPage.getSize();
		int page = topicPage.getNumber() + 1;
		int totalPages = topicPage.getTotalPages();
		long totalElements = topicPage.getTotalElements();
		PageMetadata metadata = new PageMetadata(size, page, totalElements, totalPages);
		List<TopicResponse> responses = new ArrayList<>();
		for (Topic topic : topicPage.getContent()) {
			TopicResponse response = createResponse(topic);
			responses.add(response);
		}
		
		return new PagedModel<>(responses, metadata);
	}
	
	private void createLinks(TopicResponse response) {
		Link selfLink = linkTo(methodOn(TopicController.class).getTopic(response.getId())).withSelfRel();
		Link linkToPosts = linkTo(PostController.class).slash("topics").slash(response.getId()).slash("posts").withRel("posts");
		Link linkToUser = linkTo(methodOn(UserController.class).getUser(response.getUserId())).withRel("user");
		response.add(selfLink, linkToPosts, linkToUser);
	}
}
