package com.reflex.response;


public class getOrderItemResponse {
	
	private Long id;
	private String title;
	private int amount;
	private double sum;
	private Long orderId;
	
	
	public getOrderItemResponse(Long id, String title, int amount, double sum, Long orderId) {
		this.id = id;
		this.title = title;
		this.amount = amount;
		this.sum = sum;
		this.orderId = orderId;
	}
	
	public getOrderItemResponse() {
		
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
