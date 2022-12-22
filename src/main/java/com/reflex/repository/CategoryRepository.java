package com.reflex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reflex.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	public Category findBycategoryName(String categoryName);
}
