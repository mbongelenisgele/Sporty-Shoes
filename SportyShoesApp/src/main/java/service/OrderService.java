package com.simplilearn.service;

import java.util.List;

import com.simplilearn.entity.Order;
import com.simplilearn.exception.OrderException;
import com.simplilearn.exception.ProductException;
import com.simplilearn.exception.UserException;
import com.simplilearn.model.OrderRequest;

public interface OrderService {
	String insertOrder(OrderRequest orderDTO) throws OrderException, ProductException, UserException;

	Order updateOrder(OrderRequest orderDTO) throws OrderException, ProductException;

	Integer deleteOrder(Integer orderId) throws ProductException, OrderException;
	
	List<Order> getOrdersSortedByDateCreated();
	
	List<Order> getOrdersSortedByProductCategory();
	
	List<Order> getOrdersSortedByDateUpdated();
}
