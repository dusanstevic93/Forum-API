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

import com.dusan.forum.controller.ForumController;
import com.dusan.forum.dao.ForumRepository;
import com.dusan.forum.domain.Forum;
import com.dusan.forum.exception.ForumNotFoundException;
import com.dusan.forum.request.ForumRequest;
import com.dusan.forum.response.ForumResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class ForumServiceImp implements ForumService {

	@Autowired
	private ForumRepository forumRepository;
	
	@Override
	public void createForum(long parentId, ForumRequest createForumRequest) {
		Forum forum = new Forum();
		if (parentId != 0) {
			Optional<Forum> optForum = forumRepository.findById(parentId);
			if (!optForum.isPresent())
				throw new ForumNotFoundException();
			forum.setParentForum(optForum.get());
		}
		forum.setName(createForumRequest.getName());
		forumRepository.save(forum);
		
	}

	@Override
	public PagedModel<ForumResponse> getAllForums(int page, int limit) {
		Pageable pageable = PageRequest.of(page - 1, limit);
		Page<Forum> forumPage = forumRepository.findAll(pageable);
		return createResponsePage(forumPage);
	}

	@Override
	public PagedModel<ForumResponse> getRootForums(int page, int limit) {
		Pageable pageable = PageRequest.of(page - 1, limit);
		Page<Forum> forumPage = forumRepository.findAllByParentForumNull(pageable);
		return createResponsePage(forumPage);
	}

	@Override
	public PagedModel<ForumResponse> getSubForums(long forumId, int page, int limit) {
		Optional<Forum> optForum = forumRepository.findById(forumId);
		if (!optForum.isPresent())
			throw new ForumNotFoundException();
		Pageable pageable = PageRequest.of(page - 1, limit);
		Page<Forum> forumPage = forumRepository.findAllByParentForumId(forumId, pageable);
		return createResponsePage(forumPage);
	}
	
	@Override
	public ForumResponse getForum(long forumId) {
		Optional<Forum> optForum = forumRepository.findById(forumId);
		if (!optForum.isPresent())
			throw new ForumNotFoundException();
		ForumResponse response = createResponse(optForum.get());
		return response;
	}
	
	private ForumResponse createResponse(Forum forum) {
		ForumResponse response = new ForumResponse();
		response.setId(forum.getId());
		if (forum.getParentForum() != null)
			response.setParentId(forum.getParentForum().getId());
		response.setName(forum.getName());
		createLinks(response);
		return response;
	}

	private PagedModel<ForumResponse> createResponsePage(Page<Forum> page){
		int size = page.getSize();
		int pageNumber = page.getNumber() + 1;
		int totalPages = page.getTotalPages();
		long totalElements = page.getTotalElements();
		PageMetadata metadata = new PageMetadata(size, pageNumber, totalElements, totalPages);
		List<ForumResponse> responses = new ArrayList<>();
		for (Forum forum : page.getContent()) {
			ForumResponse response = createResponse(forum);
			responses.add(response);
		}	
		return new PagedModel<>(responses, metadata);
	}
	
	private void createLinks(ForumResponse response) {
		if (response.getParentId() != 0) {
			Link linkToParent = linkTo(methodOn(ForumController.class).getForum(response.getParentId())).withRel("parent");
			response.add(linkToParent);
		}
		Link selfLink = linkTo(methodOn(ForumController.class).getForum(response.getId())).withSelfRel();
		Link linkToSubForums = linkTo(ForumController.class)
				.slash(response.getId())
				.slash("forums")
				.withRel("sub-forums");
		Link linkToTopics = linkTo(ForumController.class)
				.slash(response.getId())
				.slash("topics")
				.withRel("topics");
		response.add(selfLink, linkToSubForums, linkToTopics);
	}
}
