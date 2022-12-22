package com.reflex.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.reflex.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Arrays;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.reflex.model.User user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        List<SimpleGrantedAuthority> grantedAuthorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
	}

}