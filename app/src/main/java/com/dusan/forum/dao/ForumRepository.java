package com.dusan.forum.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dusan.forum.domain.Forum;

public interface ForumRepository extends JpaRepository<Forum, Long> {

	Page<Forum> findAllByParentForumNull(Pageable pageable);

	Page<Forum> findAllByParentForumId(long parentId, Pageable pageable);
}
