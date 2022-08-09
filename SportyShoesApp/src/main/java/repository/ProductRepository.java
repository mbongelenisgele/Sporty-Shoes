package com.simplilearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.simplilearn.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
//	This will fetch the product category for product whose product id is passed as input parameter
	@Query("SELECT C.categoryName FROM PRODUCT_CATEGORY C JOIN C.products P WHERE P.id = :productId")
	String getProductCategory(@Param("productId") Integer productId);

}
