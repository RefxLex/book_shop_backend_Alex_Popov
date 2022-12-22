package com.reflex.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="cart_items")
public class CartItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="cart_id", nullable=false)
	private Long cartId;
	
	@Column(name="book_id", nullable=false)
	private Long bookId;
	
	@Column(nullable=false)
	private int amount;
	
	@Column(nullable=false)
	private double sum;
	
	public CartItem() {
		
	}
	
	public CartItem(Long cartId, Long bookId, int amount, double sum) {

		this.cartId = cartId;
		this.bookId = bookId;
		this.amount = amount;
		this.sum = sum;
	}
	

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
}
