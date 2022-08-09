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

import com.simplilearn.exception.ProductCategoryException;
import com.simplilearn.exception.ProductException;
import com.simplilearn.model.ProductDTO;
import com.simplilearn.entity.Product;
import com.simplilearn.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	// Accessible for Admin | End Point URL -> http://localhost:9090/api/products/product
	@PostMapping("/product")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO) throws ProductException, ProductCategoryException{
		return new ResponseEntity<Product>(productService.addProduct(productDTO), HttpStatus.CREATED);
	}
	
	// Accessible for Admin | End Point URL -> http://localhost:9090/api/products/updateProduct
	@PutMapping("/updateProduct")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductDTO productDTO) throws ProductException, ProductCategoryException{
		return new ResponseEntity<Product>(productService.updateProduct(productDTO), HttpStatus.OK);
	}
	
	// Accessible for Admin | End Point URL -> http://localhost:9090/api/products/product/5
	@DeleteMapping("/product/{productId}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<String> deleteProduct(@PathVariable("productId") Integer productId) throws ProductException {
		productService.deleteProduct(productId);
		return ResponseEntity.ok("Product with "+ productId +" Deleted Successfully.");
	}
	
	// Accessible for Admin | End Point URL -> http://localhost:9090/api/products/getProduct/4
	@GetMapping("/getProduct/{productId}")
	@PreAuthorize("hasAnyRole('Admin','User')")
	public ResponseEntity<Product> getProductDetails(@PathVariable("productId") Integer productId) throws ProductException{
		return new ResponseEntity<Product>(productService.getProduct(productId), HttpStatus.FOUND);
	}
	
	// Accessible for Admin | End Point URL -> http://localhost:9090/api/products/getProducts
	@GetMapping("/getProducts")
	@PreAuthorize("hasAnyRole('Admin','User')")
	public ResponseEntity<List<Product>> getAllProducts() {
		return new ResponseEntity<List<Product>>(productService.getAllProducts(), HttpStatus.FOUND);
	}
	
	// Accessible for Admin | End Point URL -> http://localhost:9090/api/products/sortedProducts
	@GetMapping("/sortedProducts")
	@PreAuthorize("hasAnyRole('Admin','User')")
	public ResponseEntity<List<Product>> sortProductByCategory() {
		return new ResponseEntity<List<Product>>(productService.sortProductByCategory(), HttpStatus.FOUND);
	}
	
	
	

}
