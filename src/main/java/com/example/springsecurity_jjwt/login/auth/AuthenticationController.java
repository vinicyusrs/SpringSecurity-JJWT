package com.example.springsecurity_jjwt.login.auth;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity_jjwt.login.model.Role;
import com.example.springsecurity_jjwt.login.model.User;


// import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
// @RequiredArgsConstructor
	public class AuthenticationController {
		
	    private final AuthenticationService service;
	    
	    public AuthenticationController(AuthenticationService service) {
	        this.service = service;
	    }
	    
	    @GetMapping
	    public ResponseEntity<List<User>> getAllUsers() {
	        List<User> users = service.getAllUsers();
	        return ResponseEntity.ok(users);
	    }
	    
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
	        User user = service.getUserById(id);
	        if (user != null) {
	            return ResponseEntity.ok(user);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	
	    @PutMapping("/{id}")
	    public ResponseEntity<AuthenticationResponse> updateUser(@PathVariable Integer id, @RequestBody RegisterRequest updatedUser) {
	        AuthenticationResponse response = service.updateUserById(id, updatedUser);
	        if (response != null) {
	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	
	    @DeleteMapping(value = "/{id}")
	    public ResponseEntity<AuthenticationResponse> deleteUserById(@PathVariable Integer id) {
	    	AuthenticationResponse response = service.deleteUserById(id);
	    	if (response != null) {
	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	
	    @PostMapping("/register")
	    public ResponseEntity<AuthenticationResponse> register(
	            @RequestBody RegisterRequest request) { 
	    	Role role = request.getRole();
	        return ResponseEntity.ok(service.register(request, role));
	    }
	    
	    @PostMapping("/authenticate")
	    public ResponseEntity<AuthenticationResponse> authenticate(
	            @RequestBody AuthenticationRequest request)	{
	    	return ResponseEntity.ok(service.authenticate(request));
	    }
	    
    }