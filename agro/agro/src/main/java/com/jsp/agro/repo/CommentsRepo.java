package com.jsp.agro.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.agro.entity.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer> {

}
