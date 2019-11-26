package com.dusan.forum.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import com.dusan.forum.dao.PostRepository;
import com.dusan.forum.dao.TopicRepository;
import com.dusan.forum.dao.UserRepository;
import com.dusan.forum.domain.Post;
import com.dusan.forum.domain.Topic;
import com.dusan.forum.domain.User;
import com.dusan.forum.exception.PostNotFoundException;
import com.dusan.forum.exception.TopicNotFoundException;
import com.dusan.forum.exception.UserNotFoundException;
import com.dusan.forum.request.PostRequest;
import com.dusan.forum.response.PostResponse;

@Service
public class PostServiceImp implements PostService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public void createPost(long topicId, long parentId, String username, PostRequest createPostRequest) {
		Post post = new Post();
		Optional<User> optUser = userRepository.findByUsername(username);
		if (!optUser.isPresent())
			throw new UserNotFoundException();
		Optional<Topic> optTopic = topicRepository.findById(topicId);
		if (!optTopic.isPresent())
			throw new TopicNotFoundException();
		if (parentId != 0) {
			Optional<Post> optPost = postRepository.findById(parentId);
			if (!optPost.isPresent())
				throw new PostNotFoundException();
			post.setParentPost(optPost.get());
		}
		post.setText(createPostRequest.getText());
		post.setTopic(optTopic.get());
		post.setUser(optUser.get());
		postRepository.save(post);
	}

	@Override
	public PostResponse getPost(long postId) {
		Optional<Post> optPost = postRepository.findById(postId);
		if (!optPost.isPresent())
			throw new PostNotFoundException();
		Post post = optPost.get();
		return createResponse(post);
	}

	@Override
	public void deleteAnyPost(long postId) {
		Optional<Post> optPost = postRepository.findById(postId);
		if (!optPost.isPresent())
			throw new PostNotFoundException();
		postRepository.delete(optPost.get());
	}

	@Override
	public void deleteUserPost(String username, long postId) {
		Optional<Post> optPost = postRepository.findByUserUsernameAndId(username, postId);
		if (!optPost.isPresent())
			throw new PostNotFoundException();
		postRepository.delete(optPost.get());
		
	}

	@Override
	public PagedModel<PostResponse> getSubPosts(long postId, int page, int limit) {
		Optional<Post> optPost = postRepository.findById(postId);
		if (!optPost.isPresent())
			throw new PostNotFoundException();
		Pageable pageable = PageRequest.of(page, limit);
		Page<Post> postPage = postRepository.findAllByParentId(postId, pageable);
		return createResponsePage(postPage);
	}

	@Override
	public PagedModel<PostResponse> getTopicPosts(long topicId, int page, int limit) {
		Pageable pageable = PageRequest.of(page - 1, limit);
		Page<Post> postPage = postRepository.findAllByTopicId(topicId, pageable);
		return createResponsePage(postPage);
	}

	@Override
	public void editPost(long postId, String username, PostRequest editPostRequest) {
		Optional<Post> optPost = postRepository.findByUserUsernameAndId(username, postId);
		if (!optPost.isPresent())
			throw new PostNotFoundException();
		Post post = optPost.get();
		post.setText(editPostRequest.getText());
		postRepository.save(post);
	}

	@Override
	public PagedModel<PostResponse> getUserPosts(long userId, int page, int limit) {
		Pageable pageable = PageRequest.of(page - 1, limit);
		Page<Post> postPage = postRepository.findAllByUserId(userId, pageable);
		return createResponsePage(postPage);
	}

	private PostResponse createResponse(Post post) {
		PostResponse response = new PostResponse();
		response.setPostId(post.getId());
		if (post.getParent() != null)
			response.setParentId(post.getParent().getId());
		response.setTopicId(post.getTopic().getId());
		response.setText(post.getText());
		response.setCreatedOn(post.getCreatedOn());
		response.setUserId(post.getUser().getId());
		createLinks(response);
		return response;
	}

	private PagedModel<PostResponse> createResponsePage(Page<Post> postPage) {
		int size = postPage.getSize();
		int page = postPage.getNumber() + 1;
		int totalPages = postPage.getTotalPages();
		long totalElements = postPage.getTotalElements();
		PageMetadata metadata = new PageMetadata(size, page, totalElements, totalPages);
		List<PostResponse> responses = new ArrayList<>();
		for (Post post : postPage.getContent()) {
			PostResponse response = createResponse(post);
			responses.add(response);
		}
		return new PagedModel<>(responses, metadata);
	}

	private void createLinks(PostResponse response) {
		Link selfLink = linkTo(methodOn(PostController.class).getPost(response.getPostId())).withSelfRel();
		if (response.getParentId() != 0) {
			Link parentLink = linkTo(methodOn(PostController.class).getPost(response.getParentId())).withRel("parent");
			response.add(parentLink);
		}
		Link userLink = linkTo(methodOn(UserController.class).getUser(response.getUserId())).withRel("user");
		Link topicLink = linkTo(methodOn(TopicController.class).getTopic(response.getTopicId())).withRel("topic");
		response.add(selfLink, userLink, topicLink);
	}
}
