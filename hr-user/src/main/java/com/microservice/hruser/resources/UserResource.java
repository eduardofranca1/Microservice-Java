package com.microservice.hruser.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.hruser.entities.User;
import com.microservice.hruser.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserRepository repository;
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> findById(@PathVariable Long userId) {
		
		User user = repository.findById(userId).get(); // .get porque é um Optional
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/search")
	public ResponseEntity<User> findByEmail(@RequestParam String email) {
		
		User user = repository.findByEmail(email);
		return ResponseEntity.ok(user);
	}
}
