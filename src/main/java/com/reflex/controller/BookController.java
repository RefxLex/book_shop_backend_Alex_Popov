package com.reflex.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reflex.model.Author;
import com.reflex.model.Book;
import com.reflex.model.Category;
import com.reflex.repository.AuthorRepository;
import com.reflex.repository.BookRepository;
import com.reflex.repository.CategoryRepository;
import com.reflex.response.BookResponse;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired 
	CategoryRepository categoryRepository;
	
	@Autowired
	AuthorRepository authorRepository;
		
	@GetMapping("/getAll")
	public ResponseEntity<List<BookResponse>> getAllBooks(){
		List<Book> bookList = new ArrayList<Book>();
		
		List<Book> buffList = bookRepository.findAll();
		// check if book is removed
		for(int i=0; i<buffList.size(); i++) {
			if(buffList.get(i).isRemoved()!=true) {
				bookList.add(buffList.get(i));
			}
		}
		
		List<BookResponse> bookResponseList = new ArrayList<BookResponse>();
		for(int i=0; i<bookList.size(); i++) {
			
			Author author = authorRepository.findById(bookList.get(i).getAuthorId()).get();
			String titleAuthor = author.getFirstName() + author.getLastName();
			
			String category = categoryRepository.findById(bookList.get(i).getBookCategoryId()).get().getCategoryName();
			
			bookResponseList.add(new BookResponse(
					bookList.get(i).getId(),
					bookList.get(i).getYear(),
					category,
					bookList.get(i).getTitle(),
					titleAuthor,
					bookList.get(i).getPrice(),
					bookList.get(i).getDiscount(),
					bookList.get(i).getUnitInStock(),
					bookList.get(i).getBookInfo(),
					bookList.get(i).getHyperlink()
					));
		}
		
		return new ResponseEntity<>(bookResponseList, HttpStatus.OK);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories(){
		List<Category> categoryList = new ArrayList<Category>();
		
		List<Category> buffList = categoryRepository.findAll();
		// check if category is deleted
		for(int i=0; i<buffList.size(); i++) {
			if(buffList.get(i).isDeleted()!=true) {
				categoryList.add(buffList.get(i));
			}
		}
		return new ResponseEntity<>(categoryList, HttpStatus.OK);
	}
	
	// найти книги по категории
	@GetMapping("/categories/{id}")
	public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable ("id") Long id){
		List<Book> bookList = new ArrayList<Book>();	
		bookRepository.findBybookCategoryId(id).forEach(bookList::add);	
		return new ResponseEntity<>(bookList, HttpStatus.OK);
	}
	
	@GetMapping("/info/{bookId}")
	public ResponseEntity<BookResponse> getBookInfo(@PathVariable("bookId") Long bookId){
		Optional <Book> book = Optional.ofNullable(bookRepository.findById(bookId).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "No book found with id= " + bookId)));
		
		Author author = authorRepository.findById(book.get().getAuthorId()).get();
		String authorName = author.getFirstName() + " " + author.getLastName();
		Category category = categoryRepository.findById(book.get().getBookCategoryId()).get();
		
		BookResponse bookResponse = new BookResponse(
				book.get().getId(),
				book.get().getYear(),
				category.getCategoryName(),
				book.get().getTitle(),
				authorName,
				book.get().getPrice(),
				book.get().getDiscount(),
				book.get().getUnitInStock(),
				book.get().getBookInfo(),
				book.get().getHyperlink());
		
		return new ResponseEntity<>(bookResponse, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Book>> getBookByTitle (@RequestParam ("svalue") String svalue){
		
		// сначала искать по категории
		List<Book> bookList = new ArrayList<Book>();
		Optional <Category> category = Optional.ofNullable(categoryRepository.findBycategoryName(svalue));
		if (category.isPresent() == true) {
			bookRepository.findBybookCategoryId(category.get().getId()).forEach(bookList::add);
		}
		// иначе искать по названию
		if(category.isPresent() == false) {
			bookRepository.findBytitleLike("%" + svalue +"%").forEach(bookList::add);
			if (bookList.isEmpty() == true) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No books found matching your request");
			}
		}
		return new ResponseEntity<>(bookList, HttpStatus.OK);
	}
	
}
