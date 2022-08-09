package com.simplilearn.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplilearn.entity.Order;
import com.simplilearn.entity.Product;
import com.simplilearn.entity.User;
import com.simplilearn.exception.OrderException;
import com.simplilearn.exception.ProductException;
import com.simplilearn.exception.UserException;
import com.simplilearn.model.OrderRequest;
import com.simplilearn.repository.OrderRepository;
import com.simplilearn.repository.ProductRepository;
import com.simplilearn.repository.UserRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;
	private ProductRepository productRepository;
	private UserRepository userRepository;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
			UserRepository userRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	// This method will insert order into DB as per input OrderRequest object
	// provided
	@Override
	public String insertOrder(OrderRequest orderRequest) throws OrderException, ProductException, UserException {
		// Variable Initialization
		Order order = null;
		Order savedOrder = null;

		if (orderRequest != null) {
			// Fetching user from DB based on user name provided in orderRequest object or
			// else throwing user exception.
			User user = userRepository.findById(orderRequest.getUserName()).orElseThrow(() -> new UserException(
					"User not found, so cannot place this order. Please try again with valid User name."));

			// Fetching product from DB based on product id provided in orderRequest object
			// or else throwing product exception.
			Product product = productRepository.findById(orderRequest.getProductId())
					.orElseThrow(() -> new ProductException(
							"Product not found. So cannot place your order. Please try again with valid product."));
			product.setUnitsInStock(product.getUnitsInStock() - orderRequest.getTotalQuantity());

			// Creating and populating Order object to persist to DB
			order = new Order();
			order.setAddress(orderRequest.getAddress());
			order.setDateCreated(Calendar.getInstance().getTime());
			order.setLastUpdated(Calendar.getInstance().getTime());
			order.setTotalQuantity(orderRequest.getTotalQuantity());
			order.setTotalPrice(product.getUnitPrice() * orderRequest.getTotalQuantity());
			order.setProductId(product.getId());
			order.setOrderTrackingNumber(generateUniqueTrackingNumber());
			savedOrder = orderRepository.save(order);

			// Adding order object in product fetched previously and saving modified product
			// to DB.
			product.addOrder(savedOrder);
			productRepository.save(product);
			// Adding order object in user fetched previously and saving modified user to
			// DB.
			user.addOrder(savedOrder);
			userRepository.save(user);

		} else {
			throw new OrderException("Order input cannot be null");
		}
		return savedOrder.getOrderTrackingNumber();
	}

	// This method will update order as per input OrderRequest object provided
	@Override
	public Order updateOrder(OrderRequest orderRequest) throws OrderException, ProductException {
		// Fetching order from DB based on order id provided in orderRequest object or
		// else throwing order exception.
		Order order = orderRepository.findById(orderRequest.getOrderId())
				.orElseThrow(() -> new OrderException("Order Not found. Cannot update details."));

		// Fetching product from DB based on order id provided in orderRequest object or
		// else throwing product exception.
		Product product = orderRepository.getProductFromOrderId(orderRequest.getOrderId())
				.orElseThrow(() -> new ProductException(
						"Product associated with this order is removed from application. Please contact administrator."));

		if (product.getId() != orderRequest.getProductId()) {
			throw new OrderException("This order wasn't placed for product id " + orderRequest.getProductId()
					+ ". This order was placed for product id " + product.getId() + ".");
		}

		if (orderRequest.getTotalQuantity() == 0) {
			throw new OrderException("Products quantity cannot be 0 for Order");
		}
		int orderQuantity = order.getTotalQuantity();
		order.setTotalQuantity(orderRequest.getTotalQuantity());
		order.setAddress(orderRequest.getAddress());
		order.setTotalPrice(product.getUnitPrice() * order.getTotalQuantity());
		Order savedOrder = orderRepository.save(order);

		product.setUnitsInStock(product.getUnitsInStock() + (orderQuantity - orderRequest.getTotalQuantity()));
		productRepository.save(product);

		return savedOrder;
	}

	// This method will delete order from DB based on input order id
	@Override
	public Integer deleteOrder(Integer orderId) throws ProductException, OrderException {
		// Fetching order based on input order id from DB and deleting if order fetched
		// successfully or else throwing order exception.
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderException("No orders found to delete."));
		orderRepository.deleteById(orderId);

		// Fetching product based on product id associated with previously fetched order
		// or else throwing product exception
		Product product = productRepository.findById(order.getProductId()).orElseThrow(() -> new ProductException(
				"No product linked with this order. Order cannot be deleted. Please contact adminitrator."));

		// Updating Units In Stock for product as order is deleted and then saving
		// product.
		product.setUnitsInStock(product.getUnitsInStock() + order.getTotalQuantity());
		productRepository.save(product);

		return orderId;
	}

	// This method will fetch all orders from DB and sort them based on date of
	// order creation
	@Override
	public List<Order> getOrdersSortedByDateCreated() {
		// Comparator to sort List based on date creation for order
		Comparator<Order> sortByDateCreated = new Comparator<Order>() {
			@Override
			public int compare(Order O1, Order O2) {
				if (O1.getDateCreated().compareTo(O2.getDateCreated()) > 0) {
					return 1;
				} else if (O1.getDateCreated().compareTo(O2.getDateCreated()) < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		};

		// Fetching and sorting all orders from DB
		List<Order> orders = orderRepository.findAll();
		Collections.sort(orders, sortByDateCreated);

		return orders;
	}

	// This method will fetch all orders from DB and sort them based on product
	// category
	@Override
	public List<Order> getOrdersSortedByProductCategory() {
		// Comparator to sort List based on product category
		Comparator<Order> sortByProductCategory = new Comparator<Order>() {
			@Override
			public int compare(Order O1, Order O2) {
				return productRepository.getProductCategory(O1.getProductId())
						.compareTo(productRepository.getProductCategory(O2.getProductId()));
			}
		};

		// Fetching and sorting all orders from DB
		List<Order> orders = orderRepository.findAll();
		Collections.sort(orders, sortByProductCategory);

		return orders;
	}

	// This method will fetch all orders from DB and sort them based on last date of
	// order modification
	@Override
	public List<Order> getOrdersSortedByDateUpdated() {
		// Comparator to sort List based on last date of order modification
		Comparator<Order> sortByDateUpdated = new Comparator<Order>() {
			@Override
			public int compare(Order O1, Order O2) {
				return O1.getLastUpdated().compareTo(O2.getLastUpdated());
			}
		};

		// Fetching and sorting all orders from DB
		List<Order> orders = orderRepository.findAll();
		Collections.sort(orders, sortByDateUpdated);

		return orders;
	}

	public String generateUniqueTrackingNumber() {
		// Generating UUID(Unique Universal Identifier)
		return UUID.randomUUID().toString();
	}

}
