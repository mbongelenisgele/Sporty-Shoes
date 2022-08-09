package com.simplilearn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simplilearn.entity.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{
	
//	This method will fetch product category based on product category name
	public Optional<ProductCategory> findByCategoryName(String categoryName);
}
