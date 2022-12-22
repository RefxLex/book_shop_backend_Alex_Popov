package com.reflex.response;


public class getCartResponse {
	
	private Long id;
	private Long bookId;
	private Long cartId;
	private String title;
	private double price;
	private double discount;
	private int amount;
	private double sum;
	
	public getCartResponse(Long id, Long bookId, Long cartId, String title, double price, double discount, int amount,
			double sum) {
		
		this.id = id;
		this.bookId = bookId;
		this.cartId = cartId;
		this.title = title;
		this.price = price;
		this.discount = discount;
		this.amount = amount;
		this.sum = sum;
	}
	public getCartResponse() {
		
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
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
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
