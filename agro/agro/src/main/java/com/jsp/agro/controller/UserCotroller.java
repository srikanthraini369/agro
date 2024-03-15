package com.jsp.agro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.agro.entity.User;
import com.jsp.agro.service.UserService;
import com.jsp.agro.util.ResponseStructure;

@RestController
public class UserCotroller {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<User>> saveUser(@RequestBody User user) {
		return userService.save(user);
	}

	@GetMapping("/fetchById")
	public ResponseEntity<ResponseStructure<User>> fetchById(@RequestParam int id) {
		return userService.fetchById(id);
	}

	@GetMapping("/fetchAll")
	public ResponseEntity<ResponseStructure<List<User>>> fetchAll() {
		return userService.fetchAll();
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseStructure<User>> deleteById(@RequestParam int id) {
		return userService.deleteById(id);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseStructure<User>> updateMovie(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@PutMapping("/sendOtp")
	public ResponseEntity<ResponseStructure<String>> updatePassword(String email) {
		return userService.sendOTP(email);
	}

	@GetMapping("/login")
	public ResponseEntity<ResponseStructure<User>> userLogin(@RequestParam String email,
			@RequestParam String password) {
		return userService.login(email, password);
	}

}
