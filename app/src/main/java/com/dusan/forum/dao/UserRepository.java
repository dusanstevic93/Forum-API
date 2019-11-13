package com.dusan.forum.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dusan.forum.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "select u from User u join fetch u.roles", 
			countQuery = "select count(u) from User u")
	Page<User> findAllUsersJoinRoles(Pageable pageable);

	@Query("select u from User u join fetch u.roles where u.username = :username")
	Optional<User> findByUsernameJoinRoles(@Param("username") String username);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
}
