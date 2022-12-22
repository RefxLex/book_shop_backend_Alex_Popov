package com.reflex.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reflex.model.Book;
import com.reflex.model.Cart;
import com.reflex.model.CartItem;
import com.reflex.model.Category;
import com.reflex.model.Order;
import com.reflex.model.OrderItem;
import com.reflex.model.User;
import com.reflex.repository.BookRepository;
import com.reflex.repository.CartItemRepository;
import com.reflex.repository.CartRepository;
import com.reflex.repository.OrderItemRepository;
import com.reflex.repository.OrderRepository;
import com.reflex.repository.UserRepository;
import com.reflex.response.getCartResponse;
import com.reflex.response.getOrderItemResponse;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/cart/{userId}")
	public ResponseEntity<Cart> getCart(@PathVariable("userId") Long id){
		Optional<Cart> cart = Optional.ofNullable(cartRepository.findBycustomerId(id));
		if (cart.isPresent() == false) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No cart found for user with id= " + id);
		}
		return new ResponseEntity<>(cart.get(), HttpStatus.OK);
	}

	@GetMapping("/cartItems/{cartId}")
	public ResponseEntity<List<getCartResponse>> getCartItems(@PathVariable ("cartId") Long id){
		
		List<CartItem> cartItemList = new ArrayList<CartItem>();
		cartItemRepository.findBycartId(id).forEach(cartItemList::add);
		
		List<Book> bookList = new ArrayList<Book>();
		for (int j=0; j<cartItemList.size(); j++) {
			
			bookList.add(bookRepository.findById(cartItemList.get(j).getBookId()).get());
		}
		
		List<getCartResponse> cartResponseList = new ArrayList<getCartResponse>();
		
		for(int i=0; i<cartItemList.size(); i++) {
			
			cartResponseList.add(new getCartResponse(
					cartItemList.get(i).getId(),
					cartItemList.get(i).getBookId(),
					cartItemList.get(i).getCartId(),
					bookList.get(i).getTitle(),
					bookList.get(i).getPrice(),
					bookList.get(i).getDiscount(),
					cartItemList.get(i).getAmount(),
					cartItemList.get(i).getSum() ));
		}
		
		return new ResponseEntity<>(cartResponseList, HttpStatus.OK);
	}
	
	@GetMapping("/profile/{userId}")
	public ResponseEntity<User> viewUserProfile(@PathVariable("userId") Long id){
		Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND, "No such User")));
		return new ResponseEntity<>(user.get(), HttpStatus.OK);
	}
	
	@GetMapping("/orders/{userId}")
	public ResponseEntity<List<Order>> viewCustomerOrders(@PathVariable("userId") Long userId){
		Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "No such User")));
		List<Order> orderList = new ArrayList<Order>();
		orderRepository.findBycustomerId(userId).forEach(orderList::add);
		return new ResponseEntity<>(orderList, HttpStatus.OK);
	}
	
	@GetMapping("/orderItems/{orderId}")
	public ResponseEntity<List<getOrderItemResponse>> viewOrderItems(@PathVariable("orderId") Long orderId){
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		orderItemRepository.findByorderId(orderId).forEach(orderItemList::add);
		
		List<Book> bookList = new ArrayList<Book>();
		for (int j=0; j<orderItemList.size(); j++) {
			
			bookList.add(bookRepository.findById(orderItemList.get(j).getBookId()).get());
		}
		
		List<getOrderItemResponse> orderItemResponseList = new ArrayList<getOrderItemResponse>();
		
		for(int i=0; i<orderItemList.size(); i++) {
			
			orderItemResponseList.add(new getOrderItemResponse(
					orderItemList.get(i).getId(),
					bookList.get(i).getTitle(),
					orderItemList.get(i).getAmount(),
					orderItemList.get(i).getSum(),
					orderItemList.get(i).getOrderId()));
		}
			
		return new ResponseEntity<>(orderItemResponseList, HttpStatus.OK);
	}
	
	
	@PostMapping("/addBook/{userId}") 
	public ResponseEntity<CartItem> addBook(@PathVariable("userId") Long id, @RequestBody CartItem cartItem){
		
		Optional<Cart> cart = Optional.ofNullable(cartRepository.findBycustomerId(id));
		if (cart.isPresent() == false) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No cart found for user with id= " + id);
		}
		
		// check unique
		List<CartItem> checkCartItem = new ArrayList<CartItem>();
		cartItemRepository.findBycartId(cart.get().getId()).forEach(checkCartItem::add);
		for(int i=0; i<checkCartItem.size(); i++) {
			if(checkCartItem.get(i).getBookId() == cartItem.getBookId()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate cart item");
			}
		}
		CartItem localCartItem = new CartItem(cart.get().getId(),cartItem.getBookId(),cartItem.getAmount(), cartItem.getSum());
		cartItemRepository.save(localCartItem);
		return new ResponseEntity<>(localCartItem, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/createOrder")
	@Transactional
	public ResponseEntity<Order> createOrder(@RequestBody Order order){
				
		Optional<Cart> cart = Optional.ofNullable(cartRepository.findBycustomerId(order.getCustomerId()));
		if (cart.isPresent() == false) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No cart found for user with id= " + order.getCustomerId());
		}

	    Date date = new Date();
	    Long timeMilis = date.getTime();
		Timestamp currentTime = new Timestamp(timeMilis);
		
		Order localOrder = new Order(
				order.getCustomerId(),
				order.getCountry(),
				order.getCity(),
				order.getStreet(),
				order.getApartmentNumber(),
				cart.get().getTotalPrice(),
				currentTime );
		
		Order createdOrder = orderRepository.save(localOrder);
		
		// перенесём cart_items пользователя в order_items
		List<CartItem> currentCartItems = cartItemRepository.findBycartId(cart.get().getId());
		
		for(int i=0; i<currentCartItems.size(); i++) {
			CartItem cartItem = currentCartItems.get(i);
			OrderItem orderItem = new OrderItem(cartItem.getBookId(),cartItem.getAmount(),cartItem.getSum(),createdOrder.getId());
			orderItemRepository.save(orderItem);
		}
		
		return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/profile/edit/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable("userId") Long id, @RequestBody final User user){
		Optional<User> oldUser = Optional.ofNullable(userRepository.findById(id).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "No such User")));
		if (oldUser.isPresent() == true) {
			User newUser = oldUser.get();
			newUser.setEmail(user.getEmail());
			newUser.setUserName(user.getUserName());
			newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			newUser.setPhone(user.getPhone());
			
			return new ResponseEntity<>(userRepository.save(newUser),HttpStatus.OK);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such User");
		}
	}
	
	
	@PutMapping("/cartItem/update/{cartItemId}")
	public ResponseEntity<CartItem> updateCartItem(@PathVariable ("cartItemId") Long id, @RequestBody final CartItem cartItem){
		Optional<CartItem> oldCartItem = Optional.ofNullable(cartItemRepository.findById(id).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "No such cart item")));
		if(oldCartItem.isPresent() == true) {
			CartItem newCartItem = oldCartItem.get();
			newCartItem.setAmount(cartItem.getAmount());
			newCartItem.setSum(cartItem.getSum());
			
			return new ResponseEntity<>(cartItemRepository.save(newCartItem),HttpStatus.OK);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No such cart item");
		}
	}
	
	@PutMapping("/cart/update/{cartId}")
	public ResponseEntity<Cart> updateCart(@PathVariable ("cartId") Long id, @RequestBody final Cart cart){
		Optional <Cart> oldCart = Optional.ofNullable(cartRepository.findById(id).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "No cart found with id= " + id)));

		Cart newCart = oldCart.get();
		newCart.setTotalPrice(cart.getTotalPrice());
		return new ResponseEntity<>(cartRepository.save(newCart), HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/cart/{cartItemId}")
	public ResponseEntity<CartItem> deleteBook(@PathVariable("cartItemId") Long cartItemId){
		Optional <CartItem> cartItem = Optional.ofNullable(cartItemRepository.findById(cartItemId).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "No such cart_item")));
		cartItemRepository.deleteById(cartItemId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	@DeleteMapping("/clearCart/{cartId}")
	@Transactional // так как удаляются сразу несколько строк, то метод нужно объявить транзакционным
	public ResponseEntity<CartItem> clearCart(@PathVariable("cartId") Long cartId) {
		Optional <Cart> cart = Optional.ofNullable(cartRepository.findById(cartId).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart with id=" + cartId + " not found")));

		cartItemRepository.deleteBycartId(cartId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
