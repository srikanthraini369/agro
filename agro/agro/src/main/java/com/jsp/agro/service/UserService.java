package com.jsp.agro.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.jsp.agro.dao.UserDao;
import com.jsp.agro.entity.User;
import com.jsp.agro.exception.EmailWrongException;
import com.jsp.agro.exception.IdNotFoundException;
import com.jsp.agro.exception.PasswordWrongException;
import com.jsp.agro.util.ResponseStructure;

@Service
public class UserService {

	@Autowired
	private UserDao dao;
	@Autowired
	private JavaMailSender mailsender;

	ResponseStructure<User> us = new ResponseStructure<User>();

	public ResponseEntity<ResponseStructure<User>> save(User user) {
		User u = dao.findByEmail(user.getEmail());
		us.setData(dao.saveUser(user));
		us.setMessage("user saved successfull.....");
		us.setStatus(HttpStatus.CREATED.value());
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("ramansrikanth963@gmail.com");
		message.setTo(user.getEmail());
		message.setSubject("Registration Mail");
		message.setText(" Registration Successfull...Thank you for registering...!!!");
		mailsender.send(message);
		return new ResponseEntity<ResponseStructure<User>>(us, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<User>> fetchById(int id) {
		User db = dao.findUserById(id);
		if (db != null) {
			us.setData(db);
			us.setMessage("FetchById successfull....");
			us.setStatus(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<User>>(us, HttpStatus.FOUND);
		} else {
			throw new IdNotFoundException("user with" + id + "not exist");
		}
	}

	public ResponseEntity<ResponseStructure<User>> deleteById(int id) {
		User db = dao.deleteUserById(id);
		if (db != null) {
			us.setData(db);
			us.setMessage("user delete successfull....");
			us.setStatus(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<User>>(us, HttpStatus.ACCEPTED);
		} else {
			throw new IdNotFoundException("user with " + id + "is not exist");
		}
	}

	public ResponseEntity<ResponseStructure<User>> updateUser(User user) {
		User db = dao.findUserById(user.getId());
		if (db != null) {
			us.setData(dao.updateUser(user));
			us.setMessage("user update successfull....");
			us.setStatus(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<User>>(us, HttpStatus.FOUND);
		} else {
			throw new IdNotFoundException("user not fount for ur search:" + user.getId());
		}
	}

	public ResponseEntity<ResponseStructure<User>> login(String email, String password) {
		User db = dao.findByEmail(email);
		if (db == null) {
			throw new EmailWrongException(email + " is not found");
		} else {
			if (db.getPassword().equals(password)) {
				us.setData(db);
				us.setMessage("login Sucessfull");
				us.setStatus(HttpStatus.ACCEPTED.value());
				return new ResponseEntity<ResponseStructure<User>>(us, HttpStatus.ACCEPTED);
			} else {
				throw new PasswordWrongException("Password incorrect");
			}
		}
	}

	public ResponseEntity<ResponseStructure<String>> sendOTP(String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		User u = dao.findByEmail(email);
		if (u != null) {
			message.setFrom("ramansrikanth963@gmail.com");
			message.setTo(email);
			message.setSubject("OTP to chag nge Password");
			message.setText(" Your Password reset OTP is" + ((int) (Math.random() * 900000 + 100000)));
			mailsender.send(message);
			ResponseStructure<String> us = new ResponseStructure<String>();
			us.setData("OTP Send to the email id:" + email);
			us.setMessage("OTP send successfull....");
			us.setStatus(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<String>>(us, HttpStatus.ACCEPTED);
		} else {
			throw new EmailWrongException(email + "Not found");
		}
	}

	public ResponseEntity<ResponseStructure<List<User>>> fetchAll() {
		ResponseStructure<List<User>> us = new ResponseStructure<List<User>>();
		us.setData(dao.fetchAll());
		us.setMessage("Fetch Successfull");
		us.setStatus(HttpStatus.ACCEPTED.value());
		return new ResponseEntity<ResponseStructure<List<User>>>(us, HttpStatus.ACCEPTED);
	}
}
