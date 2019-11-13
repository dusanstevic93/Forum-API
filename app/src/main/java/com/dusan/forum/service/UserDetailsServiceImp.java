package com.dusan.forum.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dusan.forum.dao.UserRepository;
import com.dusan.forum.domain.User;
import com.dusan.forum.security.UserDetailsImp;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optUser = userRepository.findByUsernameJoinRoles(username);
		if (!optUser.isPresent())
			throw new UsernameNotFoundException("User does not exists");
		return new UserDetailsImp(optUser.get());
	}

}
