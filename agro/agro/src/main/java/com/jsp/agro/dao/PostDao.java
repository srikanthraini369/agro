package com.jsp.agro.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.agro.entity.Post;
import com.jsp.agro.repo.PostRepo;

@Repository
public class PostDao {
	@Autowired
	PostRepo repo;
	public Post savePost(Post post){
		return repo.save(post);
	}
	public Post fetchPostById(int id) {
		Optional<Post> post = repo.findById(id);
		if(post.isEmpty()) {
			return null;
		}
		return post.get();
	}
	public List<Post> fetchAll() {
		return repo.findAll();
		
	}
	public Post deletePostById(int id) {
		Optional<Post> db = repo.findById(id);
		if(db.isPresent()) {
			repo.deleteById(id);
			return db.get();
	}
	else{
		return null;
		
	}
	}
	public Post updatePost(Post post) {
		Optional<Post> db = repo.findById(post.getId());
		if(db.isPresent()) {
			Post p = db.get();
			if(post.getImage()==null) {
				post.setImage(p.getImage());
			}
			if(post.getCaption()==null) {
				post.setCaption(p.getCaption());
			}
			if(post.getComments()==null) {
				post.setComments(p.getComments());
			}
			if(post.getDate()==null) {
				post.setDate(p.getDate());
			}if(post.getLocation()==null) {
				post.setLocation(p.getLocation());
			}
			return repo.save(post);
		}
		return null;
	}
}














