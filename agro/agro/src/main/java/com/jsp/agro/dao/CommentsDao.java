package com.jsp.agro.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.agro.entity.Comments;
import com.jsp.agro.repo.CommentsRepo;


@Repository
public class CommentsDao {

	@Autowired
	private  CommentsRepo repo;
	
	public Comments saveComment(Comments comnt) {
		 return repo.save(comnt);
	}
	
	public Comments deleteComment(int id) {
		Optional<Comments> db = repo.findById(id);
		if(db.isPresent()) {
			repo.deleteById(id); 
			return db.get();
		}else {
			return null;
		}
	}
	
	public Comments fetchById(int id) {
		Optional<Comments> db = repo.findById(id);
		if(db.isPresent()) {
			return db.get();
		}else
		return null;
	}
	public Comments UpdateComments(Comments comments) {
		return comments;
		
	}

}







