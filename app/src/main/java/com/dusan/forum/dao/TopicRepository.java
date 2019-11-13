package com.dusan.forum.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dusan.forum.domain.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

	Page<Topic> findAllByForumId(long forumId, Pageable pageable);

	Page<Topic> findAllByUserId(long userId, Pageable pageable);

	void deleteByUserIdAndId(long userId, long topicId);
}
