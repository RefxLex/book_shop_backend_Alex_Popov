package com.reflex.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reflex.model.Author;
import com.reflex.model.Book;
import com.reflex.model.Category;
import com.reflex.model.Order;
import com.reflex.repository.AuthorRepository;
import com.reflex.repository.BookRepository;
import com.reflex.repository.CartItemRepository;
import com.reflex.repository.CategoryRepository;
import com.reflex.repository.OrderItemRepository;
import com.reflex.repository.OrderRepository;
import com.reflex.response.BookResponse;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@GetMapping("/authors")
	public ResponseEntity<List<Author>> viewAuthros(){
		List<Author> authorsList = new ArrayList<Author>();
		
		List<Author> buffList = authorRepository.findAll();
		// check if author is deleted
		for(int i=0; i<buffList.size(); i++) {
			if(buffList.get(i).isDeleted()!=true) {
				authorsList.add(buffList.get(i));
			}
		}
		return new ResponseEntity<>(authorsList, HttpStatus.OK);
	}
	
	@GetMapping("/author/{authorId}")
	public ResponseEntity<Author> getAuthor(@PathVariable ("authorId") Long authorId){
		Optional<Author> author = Optional.ofNullable(authorRepository.findById(authorId).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND,"No author found with id= "+ authorId)));
		return new ResponseEntity<>(author.get(),HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<Category> getCategory(@PathVariable ("categoryId") Long categoryId){
		Optional<Category> category = Optional.ofNullable(categoryRepository.findById(categoryId).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND,"No category found with id= " + categoryId)));
		return new ResponseEntity<>(category.get(), HttpStatus.OK);
	}
	
	@GetMapping("/order/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable ("orderId") Long orderId){
		Optional<Order> order = Optional.ofNullable(orderRepository.findById(orderId).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND,"No order found with id= " + orderId)));
		return new ResponseEntity<>(order.get(),HttpStatus.OK);
	}
	
	@PostMapping("/addAuthor")
	public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
		// check unique constraint
		List<Author> checkFisrtNameList = authorRepository.findByfirstName(author.getFirstName());
		List<Author> checkLastNameList = authorRepository.findByfirstName(author.getFirstName());
		
		if(checkFisrtNameList.isEmpty()==false && checkLastNameList.isEmpty()==false ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate author");
		}

		Author localAuthor = new Author(author.getFirstName(), author.getLastName());
		return new ResponseEntity<>(authorRepository.save(localAuthor),HttpStatus.CREATED);
	}
	
	@PostMapping("/addCategory")
	public ResponseEntity<Category> addCategory(@RequestBody Category category){
		// check unique constraint
		Optional<Category> checkCategoryName = Optional.ofNullable(categoryRepository.findBycategoryName(category.getCategoryName()));
		if(checkCategoryName.isPresent() == true) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Duplicate category");
		}
		Category localCategory = new Category(category.getCategoryName());
		return new ResponseEntity<>(categoryRepository.save(localCategory),HttpStatus.CREATED);
	}
	
	
	@PostMapping("/addBook")
	public ResponseEntity<Book> addBook(@RequestBody BookResponse book){
		
		//check author
		String authorName = book.getAuthor();
		int pos = authorName.indexOf(" ");
		String firstName = authorName.substring(0,pos);
		String lastName = authorName.substring(pos+1, authorName.length());

		List<Author> checkFirstName = authorRepository.findByfirstName(firstName);
		List<Author> checkLastName = authorRepository.findBylastName(lastName);
		if(checkFirstName.isEmpty()==true || checkLastName.isEmpty()==true) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No author found with first_name= " + firstName + " and last_name= " + lastName);
		}
		// check category
		Optional<Category> checkCategory = Optional.ofNullable(categoryRepository.findBycategoryName(book.getBookCategory()));
		if(checkCategory.isPresent()==false) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No category found with name= " + book.getBookCategory());
		}
		// check unique constraint
		Optional<Book> checkBookTitle = Optional.ofNullable(bookRepository.findBytitle(book.getTitle()));
		if(checkBookTitle.isPresent() == true) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Duplicate book title");
		}
		Book localBook = new Book(
				book.getYear(),
				checkCategory.get().getId(),
				book.getTitle(),
				checkFirstName.get(0).getId(),
				book.getPrice(),
				book.getDiscount(),
				book.getUnitInStock(),
				book.getBookInfo(),
				book.getHyperlink());
		
		return new ResponseEntity<>(bookRepository.save(localBook), HttpStatus.CREATED);
		
	}
	
	@PutMapping("/updateAuthor/{authorId}")
	public ResponseEntity<Author> updateAuthor(@PathVariable ("authorId") Long authorId, @RequestBody Author author){
		Optional<Author> oldAuthor = Optional.ofNullable(authorRepository.findById(authorId).orElseThrow(() ->
		 new ResponseStatusException(HttpStatus.NOT_FOUND, "No author found with id= " + authorId)));
		Author newAuthor = oldAuthor.get();
		newAuthor.setFirstName(author.getFirstName());
		newAuthor.setLastName(author.getLastName());
		return new ResponseEntity<>(authorRepository.save(newAuthor), HttpStatus.OK);
	}
	
	@PutMapping("/updateCategory/{categoryId}")
	public ResponseEntity<Category> updateCategory(@PathVariable ("categoryId") Long categoryId, @RequestBody Category category){
		Optional<Category> oldCategory = Optional.ofNullable(categoryRepository.findById(categoryId).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "No category found with id= " + categoryId)));
		Category newCategory = oldCategory.get();
		newCategory.setCategoryName(category.getCategoryName());
		return new ResponseEntity<>(categoryRepository.save(newCategory), HttpStatus.OK);
	}
	
	@PutMapping("/updateBook/{bookId}")
	public ResponseEntity<Book> updateBook(@PathVariable("bookId") Long bookId, @RequestBody BookResponse book){
		Optional<Book> oldBook = Optional.ofNullable(bookRepository.findById(bookId).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "No book found with id= " + bookId)));
		Book newBook = oldBook.get();
		//check author
		String authorName = book.getAuthor();
		int pos = authorName.indexOf(" ");
		String firstName = authorName.substring(0,pos);
		String lastName = authorName.substring(pos+1, authorName.length());

		List<Author> checkFirstName = authorRepository.findByfirstName(firstName);
		List<Author> checkLastName = authorRepository.findBylastName(lastName);
		if(checkFirstName.isEmpty()==true || checkLastName.isEmpty()==true) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No author found with first_name= " + firstName + " and last_name= " + lastName);
		}
		// check category
		Optional<Category> checkCategory = Optional.ofNullable(categoryRepository.findBycategoryName(book.getBookCategory()));
		if(checkCategory.isPresent()==false) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No category found with name= " + book.getBookCategory());
		}

		newBook.setAuthorId(checkFirstName.get(0).getId());
		newBook.setBookCategoryId(checkCategory.get().getId());
		newBook.setBookInfo(book.getBookInfo());
		newBook.setDiscount(book.getDiscount());
		newBook.setHyperlink(book.getHyperlink());
		newBook.setPrice(book.getPrice());
		newBook.setTitle(book.getTitle());
		newBook.setUnitInStock(book.getUnitInStock());
		newBook.setYear(book.getYear());
		
		return new ResponseEntity<>(bookRepository.save(newBook), HttpStatus.OK);
	}
	
	// soft delete
	@PutMapping("/deleteAuthor/{authorId}")
	@Transactional
	public ResponseEntity<Author> deleteAuthor(@PathVariable ("authorId") Long authorId){
		Optional<Author> oldAuthor = Optional.ofNullable(authorRepository.findById(authorId).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND,"No author found with id= "+ authorId)));
		Author newAuthor = oldAuthor.get();
		newAuthor.setDeleted(true);
		// cascade remove from sale related books
		List<Book> removeBooks = bookRepository.findByauthorId(authorId);
		for(int i=0; i<removeBooks.size(); i++) {
			removeBooks.get(i).setRemoved(true);
			// cascade delete related cart items
			cartItemRepository.deleteBybookId(removeBooks.get(i).getId());
		}
		bookRepository.saveAll(removeBooks);
		return new ResponseEntity<>(authorRepository.save(newAuthor),HttpStatus.OK);
	}
	
	// soft delete
	@PutMapping("/deleteCategory/{categoryId}")
	@Transactional
	public ResponseEntity<Category> deleteCategory(@PathVariable ("categoryId") Long categoryId){
		Optional<Category> oldCategory = Optional.ofNullable(categoryRepository.findById(categoryId).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "No category found with id= " + categoryId)));
		Category newCategory = oldCategory.get();
		newCategory.setDeleted(true);
		// cascade remove from sale related books
		List<Book> removeBooks = bookRepository.findBybookCategoryId(categoryId);
		for(int i=0; i<removeBooks.size(); i++) {
			removeBooks.get(i).setRemoved(true);
			// cascade delete related cart items
			cartItemRepository.deleteBybookId(removeBooks.get(i).getId());
		}
		bookRepository.saveAll(removeBooks);
		return new ResponseEntity<>(categoryRepository.save(newCategory), HttpStatus.OK);
	}
	
	//soft delete
	@PutMapping("/deleteBook/{bookId}")
	@Transactional
	public ResponseEntity<Book> deleteBook(@PathVariable ("bookId") Long bookId){
		Optional<Book> oldBook = Optional.ofNullable(bookRepository.findById(bookId).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "No book found with id= " + bookId)));
		Book newBook = oldBook.get();
		newBook.setRemoved(true);
		// cascade delete related cart items
		cartItemRepository.deleteBybookId(bookId);
		return new ResponseEntity<>(bookRepository.save(newBook),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteOrder/{orderId}")
	@Transactional
	public ResponseEntity<Order> deleteOrder(@PathVariable ("orderId") Long orderId){
		Optional<Order> order = Optional.ofNullable(orderRepository.findById(orderId).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "No order found with id= " + orderId)));
		// cascade delete related order items
		orderItemRepository.deleteByorderId(orderId);
		orderRepository.deleteById(orderId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
