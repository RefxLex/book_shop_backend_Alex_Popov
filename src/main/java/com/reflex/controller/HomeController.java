package com.reflex.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reflex.model.Cart;
import com.reflex.model.User;
import com.reflex.model.minor.LoginRequestBody;
import com.reflex.repository.CartRepository;
import com.reflex.repository.UserRepository;


@CrossOrigin
@RestController
@RequestMapping("/auth")
public class HomeController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	CartRepository cartRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginRequestBody loginRequestBody) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(loginRequestBody.login));	
        if(user.isPresent() == false) {	
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such User");
        }
        if (bCryptPasswordEncoder.matches(loginRequestBody.password, user.get().getPassword())) {
            user.get().setPassword(loginRequestBody.password);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
	
	@PostMapping("/register")
	public ResponseEntity<User> createUser(@RequestBody User user){
		User localUser = new User(user.getUserName(), bCryptPasswordEncoder.encode(user.getPassword()), user.getEmail(),user.getPhone(),"user");
		
		// check unique email constraint
        Optional<User> checkUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if(checkUser.isPresent() == true) {	
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }
		
		userRepository.save(localUser); 

		//пустая корзина создаётся автоматически сразу после регистрации
		Cart userCart = new Cart(localUser.getId() , 0.0);
		cartRepository.save(userCart);
		return new ResponseEntity<>(localUser, HttpStatus.CREATED);
	}
	/*
	@GetMapping("/logout")
	public ResponseEntity<User> logoutUser(HttpServletRequest request, HttpServletResponse response)
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		// redirect
	return ResponseEntity */

}
