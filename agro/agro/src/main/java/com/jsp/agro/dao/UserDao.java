package com.jsp.agro.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.agro.entity.Image;
import com.jsp.agro.entity.User;
import com.jsp.agro.repo.UserRepo;

@Repository
public class UserDao {

	@Autowired
	private UserRepo repo;

	public User saveUser(User user) {
		return repo.save(user);
	}

	public User findUserById(int id) {
		Optional<User> db = repo.findById(id);
		if (db.isPresent())
			return db.get();
		else
			return null;
	}

	public List<User> fetchAll() {
		return repo.findAll();
	}

	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public User findByImage(Image image) {
		return repo.findByImage(image);

	}

	public User updateUser(User user) {
		Optional<User> db = repo.findById(user.getId());
		if (db.isPresent()) {
			User userdb = db.get();
			if (user.getFirstName() == null) {
				user.setFirstName(userdb.getFirstName());
			}
			if (user.getLastName() == null) {
				user.setLastName(userdb.getLastName());
			}
			if (user.getEmail() == null) {
				user.setEmail(userdb.getLastName());
			}
			if (user.getPhone() == 0) {
				user.setPhone(userdb.getPhone());
			}
			if (user.getPassword() == null) {
				user.setPassword(userdb.getPassword());
			}
			if (user.getFirstName() == null) {
				user.setFirstName(userdb.getFirstName());
			}
			if (user.getGender() == null) {
				user.setGender(userdb.getGender());
			}
			if (user.getAge() == 0) {
				user.setAge(userdb.getAge());
			}
			if (user.getType() != null) {
				user.setType(userdb.getType());
			}
			if (user.getAddress() != null) {
				user.setAddress(userdb.getAddress());
			}
			if (user.getImage() != null) {
				user.setImage(userdb.getImage());
			}
			if (user.getPost() != null) {
				user.setPost(userdb.getPost());
			}
			return repo.save(user);
		}
		return user;
	}

	public User deleteUserById(int id) {
		Optional<User> db = repo.findById(id);
		if (db.isPresent()) {
			repo.deleteById(id);
			return db.get();
		} else {
			return null;
		}
	}

	

}
