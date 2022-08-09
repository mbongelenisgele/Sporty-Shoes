package com.simplilearn.service;

import java.util.List;

import com.simplilearn.entity.Product;
import com.simplilearn.exception.ProductCategoryException;
import com.simplilearn.exception.ProductException;
import com.simplilearn.model.ProductDTO;

public interface ProductService {

	Product addProduct(ProductDTO productDTO) throws ProductException, ProductCategoryException;

	Product updateProduct(ProductDTO productDTO) throws ProductException, ProductCategoryException ;

	void deleteProduct(Integer productId) throws ProductException ;
	
	Product getProduct(Integer productId) throws ProductException ;

	List<Product> getAllProducts();

	List<Product> sortProductByCategory();

}
