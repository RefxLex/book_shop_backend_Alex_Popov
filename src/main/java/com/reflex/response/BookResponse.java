package com.reflex.response;


public class BookResponse {
	
	private Long id;
	private int year;
	private String bookCategory;
	private String title;
	private String author;
	private double price;
	private double discount;
	private int unitInStock;
	private String bookInfo;
	private String hyperlink;
	
	public BookResponse() {
		
	}

	public BookResponse(Long id, int year, String bookCategory, String title, String author, double price,
			double discount, int unitInStock, String bookInfo, String hyperlink) {

		this.id = id;
		this.year = year;
		this.bookCategory = bookCategory;
		this.title = title;
		this.author = author;
		this.price = price;
		this.discount = discount;
		this.unitInStock = unitInStock;
		this.bookInfo = bookInfo;
		this.hyperlink = hyperlink;
	}
	
	
	public Long getId() {
		return id;
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
	public String getBookCategory() {
		return bookCategory;
	}
	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
	public String getBookInfo() {
		return bookInfo;
	}
	public void setBookInfo(String bookInfo) {
		this.bookInfo = bookInfo;
	}

	public String getHyperlink() {
		return hyperlink;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}
	
}
