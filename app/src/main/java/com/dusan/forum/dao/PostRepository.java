package com.dusan.forum.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dusan.forum.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findAllByTopicId(long topicId, Pageable pageable);

	Page<Post> findAllByUserId(long userId, Pageable pageable);

	void deleteByUserIdAndId(long userId, long postId);
}
