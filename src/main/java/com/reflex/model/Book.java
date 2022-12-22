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
@Table(name="books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private int year;

	@Column(name="book_category_id", nullable=false)
	private Long bookCategoryId;
	
	@Column(nullable=false, unique=true)
	private String title;
 
	@Column(name="author_id", nullable = false)
	private Long authorId;
	
	@Column(nullable=false)
	private double price;
	
	@Column(nullable=false)
	private double discount;
	
	@Column(name="unit_in_stock",nullable=false)
	private int unitInStock;
	
	@Column(name="book_info", nullable=false)
	private String bookInfo;
	
	@Column(nullable=false)
	private String hyperlink;
	
	@Column(nullable=false)
	private boolean removed;
	
	/*
	@OneToMany(mappedBy="book")
	private Set<CartItem> cartItems;	*/
	

	public Book () {
		
	}
	
	public Book(int year, Long bookCategoryId, String title, Long authorId, double price, double discount,
			int unitInStock, String bookInfo, String hyperlink) {

		this.year = year;
		this.bookCategoryId = bookCategoryId;
		this.title = title;
		this.authorId = authorId;
		this.price = price;
		this.discount = discount;
		this.unitInStock = unitInStock;
		this.bookInfo = bookInfo;
		this.hyperlink = hyperlink;
		this.removed = false;
	}

	public String getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(String bookInfo) {
		this.bookInfo = bookInfo;
	}

	public Long getId() {
		return id;
	}
	public Long getBookCategoryId() {
		return bookCategoryId;
	}

	public void setBookCategoryId(Long bookCategoryId) {
		this.bookCategoryId = bookCategoryId;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
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
	public int getUnitInStock() {
		return unitInStock;
	}
	public void setUnitInStock(int unitInStock) {
		this.unitInStock = unitInStock;
	}

	public String getHyperlink() {
		return hyperlink;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	
}
