package com.jsp.agro.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.agro.dao.PostDao;
import com.jsp.agro.dao.UserDao;
import com.jsp.agro.entity.Image;
import com.jsp.agro.entity.Post;
import com.jsp.agro.entity.User;
import com.jsp.agro.exception.IdNotFoundException;
import com.jsp.agro.exception.PostNotFoundException;
import com.jsp.agro.util.ResponseStructure;

@Service
public class PostService {
	@Autowired
	PostDao pdao;
	@Autowired
	UserDao udao;
	
	ResponseStructure<Post> rs= new ResponseStructure<Post>();
	public ResponseEntity<ResponseStructure<Post>> savePost(int userId, MultipartFile file, String caption, String location) throws IOException{
		User user=udao.findUserById(userId);
		if(user!=null) {
			Image image= new Image();
			image.setName(file.getOriginalFilename());
			image.setData(file.getBytes());
			Post post=new Post();
			post.setImage(image);
			post.setCaption(caption);
			post.setLocation(location);
			post.setDate(LocalDateTime.now());
			pdao.savePost(post);
			List<Post> p=new ArrayList<Post>();
			p.add(post);
			p.addAll(user.getPost());
			user.setPost(p);
			udao.updateUser(user);
			rs.setMessage("Post uplaoded successfully");
			rs.setStatus(HttpStatus.ACCEPTED.value());
			rs.setData(post);
			return new ResponseEntity<ResponseStructure<Post>>(rs,HttpStatus.ACCEPTED);
		}
		else {
			throw new IdNotFoundException("User not found with id: "+ userId);
		}
	}
	public ResponseEntity<ResponseStructure<Post>> fetchPostById(int id){
		Post post=pdao.fetchPostById(id);
		if(post!=null) {
			rs.setMessage("Post Fetched successfully");
			rs.setStatus(HttpStatus.FOUND.value());
			rs.setData(post);
			return new ResponseEntity<ResponseStructure<Post>>(rs,HttpStatus.FOUND);
		}
		else {
			throw new PostNotFoundException("Post not found with Id: "+id);
		}
	}
	public ResponseEntity<ResponseStructure<Post>> deletePost(int id) {
		Post post=pdao.fetchPostById(id);
		if(post!=null) {
			List<User> users= udao.fetchAll();
			for(User u:users) {
				List<Post> posts=u.getPost();
				if(posts.contains(post)) { 
				posts.remove(post);
				udao.updateUser(u);
				pdao.deletePostById(id);
				break;
				}
			}
		ResponseStructure<Post> s=new ResponseStructure<Post>();
		s.setData(post); 
		s.setMessage("post deleted");
		s.setStatus(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<Post>>(s,HttpStatus.OK);	
		}
		throw new PostNotFoundException("Post not found with Id: "+id);
	}
}






















