package com.dusan.forum.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dusan.forum.controller.AccountController;
import com.dusan.forum.dao.UserRepository;
import com.dusan.forum.dao.VerificationTokenRepository;
import com.dusan.forum.domain.User;
import com.dusan.forum.domain.VerificationToken;
import com.dusan.forum.exception.ExpiredLinkException;
import com.dusan.forum.exception.InvalidLinkException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class AccountServiceImp implements AccountService {

	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void activate(String token) {
		Optional<VerificationToken> optToken = tokenRepository.findByToken(token);
		if (!optToken.isPresent())
			throw new InvalidLinkException();
		VerificationToken vt = optToken.get();
		if (LocalDateTime.now().compareTo(vt.getExpire()) > 0)
			throw new ExpiredLinkException();
		User user = vt.getUser();
		user.setEnabled(true);
		userRepository.save(user);
		tokenRepository.delete(vt);
	}

	@Override
	public void sendActivationMail(User user) {
		String token = UUID.randomUUID().toString();
		MimeMessage message = mailSender.createMimeMessage();
		String link = linkTo(AccountController.class).toString();
		String text = "<a href=" + link + "?token=" + token + ">Click here to activate</a>";
		
		try {
			message.setSubject("Email verification");
			message.setFrom("com.dusanstevic93@gmail.com");
			message.addRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));
			message.setContent(text, "text/html");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mailSender.send(message);
		
		VerificationToken vt = new VerificationToken(token);
		vt.setUser(user);
		tokenRepository.save(vt);
	}

	@Override
	public void resendActivationMail(String token) {
		Optional<VerificationToken> optToken = tokenRepository.findByToken(token);
		if (!optToken.isPresent())
			throw new InvalidLinkException();
		VerificationToken verificationToken = optToken.get();
		User user = verificationToken.getUser();
		tokenRepository.delete(verificationToken);
		sendActivationMail(user);
	}
	
	
}
