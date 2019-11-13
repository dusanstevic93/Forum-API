package com.dusan.forum.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dusan.forum.controller.PostController;
import com.dusan.forum.controller.TopicController;
import com.dusan.forum.controller.UserController;
import com.dusan.forum.dao.RoleRepository;
import com.dusan.forum.dao.UserRepository;
import com.dusan.forum.dao.VerificationTokenRepository;
import com.dusan.forum.domain.User;
import com.dusan.forum.domain.VerificationToken;
import com.dusan.forum.exception.EmailExistsException;
import com.dusan.forum.exception.UserNotFoundException;
import com.dusan.forum.exception.UsernameExistsException;
import com.dusan.forum.request.UserRequest;
import com.dusan.forum.response.UserResponse;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void createUser(UserRequest userRequest) {
		User user = new User();
		Optional<User> optUser = userRepository.findByUsername(userRequest.getUsername());
		if (optUser.isPresent())
			throw new UsernameExistsException();
		Optional<User> optUser2 = userRepository.findByEmail(userRequest.getEmail());
		if (optUser2.isPresent())
			throw new EmailExistsException();
		user.setUsername(userRequest.getUsername());
		user.setPassword(encoder.encode(userRequest.getPassword()));
		user.setEmail(userRequest.getEmail());
		user.addRole(roleRepository.getOne(2L));
		user.setEnabled(true); // disable email verification
		userRepository.save(user);
		
		
		/*String token = UUID.randomUUID().toString();
		SimpleMailMessage message = new SimpleMailMessage();
		String link = linkTo(UserController.class).slash("account-activation").toString();
		message.setSubject("Email verification");
		message.setFrom("com.dusanstevic93@gmail.com");
		message.setTo(user.getEmail());
		message.setText("Verification link: " + link + "?token=" + token);
		mailSender.send(message);
		
		VerificationToken vt = new VerificationToken(token);
		vt.setUser(user);
		verificationTokenRepository.save(vt);*/
	}

	@Override
	public String activateAccount(String token) {
		Optional<VerificationToken> optToken = verificationTokenRepository.findByToken(token);
		if (!optToken.isPresent())
			return "Invalid link";
		VerificationToken vt = optToken.get();
		if (LocalDateTime.now().compareTo(vt.getExpire()) > 0)
			return "Expired link";
		User user = vt.getUser();
		user.setEnabled(true);
		userRepository.save(user);
		verificationTokenRepository.delete(vt);
		return "Activation successful";
		
	}

	@Override
	public PagedModel<UserResponse> getUsers(int page, int limit) {
		Pageable pageable = PageRequest.of(page - 1, limit);
		Page<User> userPage = userRepository.findAllUsersJoinRoles(pageable);
		return createResponsePage(userPage);
	}

	@Override
	public UserResponse getUser(long userId) {
		Optional<User> optUser = userRepository.findById(userId);
		if (!optUser.isPresent())
			throw new UserNotFoundException();
		User user = optUser.get();
		return createResponse(user);
		
	}
	
	private UserResponse createResponse(User user) {
		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		user.getRoles().forEach(role -> response.addRole(role.getName()));
		createLinks(response);
		return response;
	}
	
	private PagedModel<UserResponse> createResponsePage(Page<User> userPage){
		int size = userPage.getSize();
		int page = userPage.getNumber() + 1;
		long totalElements = userPage.getTotalElements();
		int totalPages = userPage.getTotalPages();
		PageMetadata metadata = new PageMetadata(size, page, totalElements, totalPages);
		List<UserResponse> responses = new ArrayList<>();
		for (User user : userPage.getContent()) {
			UserResponse response = createResponse(user);
			responses.add(response);
		}
		return new PagedModel<>(responses, metadata);
	}
	
	private void createLinks(UserResponse response) {
		Link selfLink = linkTo(methodOn(UserController.class).getUser(response.getId())).withSelfRel();
		Link linkToTopics = linkTo(TopicController.class).slash("users").slash(response.getId()).slash("topics").withRel("topics");
		Link linkToPosts = linkTo(PostController.class).slash("users").slash(response.getId()).slash("posts").withRel("posts");
		response.add(selfLink, linkToTopics, linkToPosts);
	}

}
