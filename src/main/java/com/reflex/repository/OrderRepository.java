package com.reflex.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.reflex.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	public List<Order> findBytimeSubmitted(Timestamp timeSubmitted);
	public List<Order> findBycustomerId(Long customerId);

}
