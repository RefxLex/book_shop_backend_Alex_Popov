package com.reflex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.reflex.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	/*
	@Modifying(flushAutomatically = true)
	@Query("delete CartItem i where i.cartId = cart_id")
	void deleteByCartId(@Param("cart_id") Long cart_id);*/
	
	void deleteBycartId(Long cart_id);
	void deleteBybookId(Long bookId);
	
	public List<CartItem> findBycartId(Long cartId);

}
