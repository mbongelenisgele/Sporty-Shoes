package com.simplilearn.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplilearn.entity.Order;
import com.simplilearn.exception.OrderException;
import com.simplilearn.exception.ProductException;
import com.simplilearn.exception.UserException;
import com.simplilearn.model.OrderRequest;
import com.simplilearn.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	// Accessible for User | End Point URL -> http://localhost:9090/api/orders/order
	@PostMapping("/order")
    @PreAuthorize("hasRole('User')")
	public ResponseEntity<String > placeOrder(@Valid @RequestBody OrderRequest orderDTO) throws OrderException, ProductException, UserException{
		String preMessage = "Order Successfully placed. Order Tracking Number is ";
		return new  ResponseEntity<String>(preMessage+orderService.insertOrder(orderDTO), HttpStatus.OK);
	}
	
	// Accessible for User | End Point URL -> http://localhost:9090/api/orders/modifyOrder
	@PutMapping("/modifyOrder")
    @PreAuthorize("hasRole('User')")
	public ResponseEntity<Order> modifyOrder(@Valid @RequestBody OrderRequest orderDTO) throws OrderException, ProductException{
		return new ResponseEntity<Order>(orderService.updateOrder(orderDTO), HttpStatus.OK);
	}
	
	// Accessible for User | End Point URL -> http://localhost:9090/api/orders/deleteOrder/1
	@DeleteMapping("/deleteOrder/{orderId}")
    @PreAuthorize("hasAnyRole('Admin','User')")
	public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Integer orderId) throws ProductException, OrderException{
		String message = "Order with order id " + orderService.deleteOrder(orderId) + " deleted successfully.";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	// Accessible for Admin | End Point URL -> http://localhost:9090/api/orders/orderByDateCreated
	@GetMapping("/orderByDateCreated")
    @PreAuthorize("hasAnyRole('Admin','User')")
	public ResponseEntity<List<Order>> getOrdersSortedByDateCreated(){
		return new ResponseEntity<List<Order>>(orderService.getOrdersSortedByDateCreated(), HttpStatus.OK);
	}
	
	// Accessible for Admin | End Point URL -> http://localhost:9090/api/orders/orderByProductCategory
	@GetMapping("/orderByProductCategory")
    @PreAuthorize("hasAnyRole('Admin','User')")
	public ResponseEntity<List<Order>> getOrdersSortedByProductCategory(){
		return new ResponseEntity<List<Order>>(orderService.getOrdersSortedByProductCategory(), HttpStatus.OK);
	}
	
	// Accessible for Admin | End Point URL -> http://localhost:9090/api/orders/orderByDateUpdated
	@GetMapping("/orderByDateUpdated")
    @PreAuthorize("hasAnyRole('Admin','User')")
	public ResponseEntity<List<Order>> getOrdersSortedByDateUpdated(){
		return new ResponseEntity<List<Order>>(orderService.getOrdersSortedByDateUpdated(), HttpStatus.OK);
	}
}
