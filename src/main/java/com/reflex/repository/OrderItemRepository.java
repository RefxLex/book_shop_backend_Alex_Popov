package com.reflex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reflex.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	public List<OrderItem> findByorderId(Long orderId);
	public void deleteByorderId(Long orderId);
}
