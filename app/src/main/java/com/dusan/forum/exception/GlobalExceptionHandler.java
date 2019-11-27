package com.dusan.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.dusan.forum.response.ExceptionResponse;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse handleException(Exception e) {
		return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
	
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handleException(ForumNotFoundException e) {
		return new ExceptionResponse(HttpStatus.NOT_FOUND, "Forum does not exists");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handleException(UserNotFoundException e) {
		return new ExceptionResponse(HttpStatus.NOT_FOUND, "User does not exists");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handleException(TopicNotFoundException e) {
		return new ExceptionResponse(HttpStatus.NOT_FOUND, "Topic does not exists");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handleException(PostNotFoundException e) {
		return new ExceptionResponse(HttpStatus.NOT_FOUND, "Post does not exists");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleException(MethodArgumentNotValidException ex) {
		StringBuilder errors = new StringBuilder();
		ex.getBindingResult().getAllErrors().forEach(e -> errors.append(e.getDefaultMessage() + ";"));
		return new ExceptionResponse(HttpStatus.BAD_REQUEST, errors.toString());
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleException(HttpMessageNotReadableException e) {
		return new ExceptionResponse(HttpStatus.BAD_REQUEST, "JSON parsing error");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleException(MethodArgumentTypeMismatchException e) {
		String message = e.getName() + " should be of type " + e.getRequiredType();
		return new ExceptionResponse(HttpStatus.BAD_REQUEST, message);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleException(MissingServletRequestParameterException e) {
		String message = "Required parameter " + e.getParameterName() + 
				" of type " + e.getParameterType() + " is missing";
		return new ExceptionResponse(HttpStatus.BAD_REQUEST, message);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ExceptionResponse handleException(AccessDeniedException e) {
		return new ExceptionResponse(HttpStatus.FORBIDDEN, "Unautorized access");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ExceptionResponse handleException(DisabledException e) {
		return new ExceptionResponse(HttpStatus.FORBIDDEN, "Account is not activated");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public ExceptionResponse handleException(UsernameExistsException e) {
		return new ExceptionResponse(HttpStatus.CONFLICT, "Username already exists");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public ExceptionResponse handleException(EmailExistsException e) {
		return new ExceptionResponse(HttpStatus.CONFLICT, "Email already exists");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ExceptionResponse handleException(BadCredentialsException e) {
		return new ExceptionResponse(HttpStatus.FORBIDDEN, "Bad credentials");
	}
	
	
}
