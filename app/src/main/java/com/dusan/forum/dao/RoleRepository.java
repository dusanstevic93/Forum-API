package com.dusan.forum.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dusan.forum.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
