package com.reflex.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="order_items")
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="book_id", nullable=false)
	private Long bookId;
	
	@Column(nullable=false)
	private int amount;
	
	@Column(nullable=false)
	private double sum;
	
	@Column(name="order_id", nullable=false)
	private Long orderId;
	
	
	public OrderItem(Long bookId, int amount, double sum, Long orderId) {

		this.bookId = bookId;
		this.amount = amount;
		this.sum = sum;
		this.orderId = orderId;
	}

	public OrderItem() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
