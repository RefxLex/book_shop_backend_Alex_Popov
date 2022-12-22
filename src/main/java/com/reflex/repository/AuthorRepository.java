package com.reflex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reflex.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	public List<Author> findByfirstName(String fisrtName);
	public List<Author> findBylastName (String lastName);
}
