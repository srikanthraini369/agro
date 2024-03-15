package com.jsp.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.agro.entity.Comments;
import com.jsp.agro.service.CommentsService;
import com.jsp.agro.util.ResponseStructure;

@RestController
public class CommentsController {
	
	@Autowired
	CommentsService service;
	
	@PostMapping("/savecomment")
	public ResponseEntity<ResponseStructure<Comments>> uploadComments(@RequestParam int postid, @RequestParam int userid, @RequestParam String comment){
		return service.saveComments(postid, userid, comment);
	}
	@DeleteMapping("/deletecomment")
	public ResponseEntity<ResponseStructure<Comments>> deleteComments(@RequestParam int id) {
		return service.deleteComment(id);
		
	}
}
