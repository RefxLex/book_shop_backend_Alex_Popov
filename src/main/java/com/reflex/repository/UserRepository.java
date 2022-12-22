package com.reflex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reflex.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail (String email);
	User findBypassword (String password);
	User findByphone(String phone);
}
