package com.jsp.agro.entity;

import java.util.List;


import com.jsp.agro.enums.UserType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "firstName cannot be blank")
	@NotNull(message = "firstName cannot be null")
	private String firstName;
	
	@NotBlank(message = "lastName cannot be blank")
	@NotNull(message = "lastName cannot be null")
	private String lastName;
	
	@Min(value= 6000000000l, message = " phone number must be valid" )
	@Max(value= 9999999999l, message = " phone number must be valid" )
	private long phone;
	
//	@Column(unique = true)
//	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
//	@NotBlank(message = "Email cannot be blank")
//	@NotNull(message = "Email cannot be null")
	private String email;
	
//	@NotBlank(message = "Password is required")
//	@Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
//	@Pattern(regexp =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "must contain at least one letter, one number, one special character")
	private String password;
	private String gender;
	private int age;
	@Enumerated(EnumType.STRING)
	private UserType type;
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	@OneToOne(cascade = CascadeType.ALL)
	private Image image;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Post> post;
}
