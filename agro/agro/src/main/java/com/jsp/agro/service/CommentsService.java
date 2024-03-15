package com.jsp.agro.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.agro.dao.CommentsDao;
import com.jsp.agro.dao.PostDao;
import com.jsp.agro.dao.UserDao;
import com.jsp.agro.entity.Comments;
import com.jsp.agro.entity.Post;
import com.jsp.agro.entity.User;
import com.jsp.agro.exception.CommentNotFoundException;
import com.jsp.agro.exception.IdNotFoundException;
import com.jsp.agro.util.ResponseStructure;

@Service
public class CommentsService {

	@Autowired
	private CommentsDao cdao;

	@Autowired
	private PostDao pdao;

	@Autowired
	private UserDao udao;

	ResponseStructure<Comments> rs = new ResponseStructure<Comments>();

	public ResponseEntity<ResponseStructure<Comments>> saveComments(int postid, int userid, String message) {
		Post post = pdao.fetchPostById(postid);
		if (post != null) {
			User user = udao.findUserById(userid);
			if (user != null) {
				Comments c = new Comments();
				c.setComment(message);
				c.setUser(user);
				cdao.saveComment(c);
				List<Comments> clist = new ArrayList<Comments>();
				clist.add(c);
				clist.addAll(post.getComments());
				post.setComments(clist);
				pdao.savePost(post);
				rs.setMessage("Commented succesfully");
				rs.setStatus(HttpStatus.OK.value());
				rs.setData(c);
				return new ResponseEntity<ResponseStructure<Comments>>(rs, HttpStatus.OK);
			} else {
				throw new IdNotFoundException("user not found for your search:"+userid);
			}
		} else
			throw new CommentNotFoundException("Comment not found");
	}
	
	public ResponseEntity<ResponseStructure<Comments>> deleteComment(int id){
		Comments comm = cdao.fetchById(id);
		if(comm!=null) {
			List<Post> li = pdao.fetchAll();
			for (Post post : li) {
				List<Comments> c = post.getComments();
				if(c.contains(comm)) {
					c.remove(comm);
					pdao.updatePost(post);
					cdao.deleteComment(id);
					break;
					
				}
			}
			rs.setData(comm);
			rs.setMessage("Comments deleted");
			rs.setStatus(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Comments>>(rs,HttpStatus.OK);
		}
		else {
			throw new CommentNotFoundException("comment not found for your search:"+id);
		}
	}
	
	
	
	
	

//	public ResponseEntity<ResponseStructure<Comments>> deleteComment(int id){
//		Comments cdata = cdao.fetchById(id);
//		if(cdata!=null) {
//			cdao.deleteComment(id);
//			rs.setData(cdata);
//			rs.setMessage("Comment deleted");
//			rs.setStatus(HttpStatus.FOUND.value());
//			return new ResponseEntity<ResponseStructure<Comments>>(rs,HttpStatus.FOUND);
//		}
//		else {
//			throw new CommentNotFoundException();
//		}
//	}
}
