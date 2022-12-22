package com.reflex.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import java.sql.Timestamp;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="customer_id", nullable=false)
	private Long customerId;
	
	@Column(nullable=false)
	private String country;
	
	@Column(nullable=false)
	private String city;
	
	@Column(nullable=false)
	private String street;
	
	@Column(name="apartment_number", nullable=false)
	private int apartmentNumber;
	
	@Column(name="total_price", nullable=false)
	private double totalPrice;
	
	@Column(nullable=false)
	private Timestamp timeSubmitted;
	
	public Order() {
		
	}
	
	public Order(Long customerId, String country, String city, String street, int apartmentNumber, double totalPrice, Timestamp timeSubmitted) {
		
		this.customerId = customerId;
		this.country = country;
		this.city = city;
		this.street = street;
		this.apartmentNumber = apartmentNumber;
		this.totalPrice = totalPrice;
		this.timeSubmitted = timeSubmitted;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Timestamp getTimeSubmitted() {
		return timeSubmitted;
	}

	public void setTimeSubmitted(Timestamp timeSubmitted) {
		this.timeSubmitted = timeSubmitted;
	}
	
}
