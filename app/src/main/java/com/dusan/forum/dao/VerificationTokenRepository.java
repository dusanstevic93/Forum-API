package com.dusan.forum.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dusan.forum.domain.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(String token);
}
