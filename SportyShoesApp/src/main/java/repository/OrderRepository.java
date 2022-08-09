package com.simplilearn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.simplilearn.entity.Order;
import com.simplilearn.entity.Product;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	// This will fetch product associated with order whose order id is passed as input parameter.
	@Query("SELECT P FROM PRODUCTS P JOIN P.orders O WHERE O.orderId = :orderId")
	Optional<Product> getProductFromOrderId(@Param("orderId") Integer orderId);
}
