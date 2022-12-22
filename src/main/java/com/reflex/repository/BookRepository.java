package com.reflex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reflex.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	
	public List<Book> findBybookCategoryId(Long categoryId);
	public List<Book> findBytitleLike(String title);
	public List<Book> findByauthorId(Long authorId);
	public Book findBytitle(String title);

}
