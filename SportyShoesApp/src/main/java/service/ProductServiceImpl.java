package com.simplilearn.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplilearn.entity.Product;
import com.simplilearn.entity.ProductCategory;
import com.simplilearn.exception.ProductCategoryException;
import com.simplilearn.exception.ProductException;
import com.simplilearn.model.ProductDTO;
import com.simplilearn.repository.ProductCategoryRepository;
import com.simplilearn.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private ProductCategoryRepository productCategoryRepository;

	// List of valid product categories -> SPORTS, TREKKING, FORMAL, CASUAL, LOAFER.
	String[] productCategories = { "SPORTS", "TREKKING", "FORMAL", "CASUAL", "LOAFER" };

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository,
			ProductCategoryRepository productCategoryRepository) {
		this.productRepository = productRepository;
		this.productCategoryRepository = productCategoryRepository;
	}

	// This method will insert product into DB as per input productDTO object
	// provided.
	@Override
	public Product addProduct(ProductDTO productDTO) throws ProductException, ProductCategoryException {
		// Initialization
		Product product = null;
		Product savedProduct = null;
		ProductCategory productCategory = null;
		Boolean validCategoryFlag = false;

		// If input productDTO is null then throwing Product Exception.
		if (productDTO == null) {
			throw new ProductException("Product Input is NULL");
		} else {

			// Validating product category. if product category provided is not valid then
			// throwing Product Category Exception.
			for (String category : productCategories) {
				if (category.equals(productDTO.getProductCategoryName()))
					validCategoryFlag = true;
			}
			if (!validCategoryFlag)
				throw new ProductCategoryException(
						"Provided input product category is not valid. Valid product categories are ['SPORTS','TREKKING','FORMAL','CASUAL','LOAFER']");

			// If product category provided in input productDTO is exists in DB then
			// fetching product category or else creating new product category to persist it
			// to DB along with product.
			if (productCategoryRepository.findByCategoryName(productDTO.getProductCategoryName()).isPresent()) {
				productCategory = productCategoryRepository.findByCategoryName(productDTO.getProductCategoryName())
						.orElseThrow(() -> new ProductCategoryException(
								"Please provide valid product category. Valid product categories are ['SPORTS','TREKKING','FORMAL','CASUAL','LOAFER']"));
			} else {
				productCategory = new ProductCategory();
				productCategory.setCategoryName(productDTO.getProductCategoryName());
				productCategory.setCategoryDescription(productDTO.getProductCategoryDescription());
			}

			// Creating new Product object and populating data and persisting it to DB.
			product = new Product();
			product.setProductName(productDTO.getProductName());
			product.setProductDescription(productDTO.getProductDescription());
			product.setManufacturer(productDTO.getManufacturer());
			product.setUnitPrice(productDTO.getUnitPrice());
			product.setUnitsInStock(productDTO.getUnitsInStock());
			savedProduct = productRepository.save(product);

			// Adding product to product category and persisting modified product category
			// to DB.
			productCategory.addProduct(savedProduct);
			productCategoryRepository.save(productCategory);

			return savedProduct;
		}
	}

	// This method will update product from DB as per input productDTO object
	// provided.
	@Override
	public Product updateProduct(ProductDTO productDTO) throws ProductException, ProductCategoryException {
		// Initialization
		Product product = null;

		if(productDTO.getProductId() == null) {
			throw new ProductException("Please provide product id to update the product.");
		}
		// If product id provided in input productDTO is exists in DB then fetching
		// corresponding product or else throwing Product Exception.
		product = productRepository.findById(productDTO.getProductId())
				.orElseThrow(() -> new ProductException("Product not found. Please try again with valid product id."));

		// Creating new Product object and populating data and persisting it to DB.
		product.setProductName(productDTO.getProductName());
		product.setProductDescription(productDTO.getProductDescription());
		product.setManufacturer(productDTO.getManufacturer());
		product.setUnitPrice(productDTO.getUnitPrice());
		product.setUnitsInStock(productDTO.getUnitsInStock());

		// Saving modified product to DB and resturning it.
		return productRepository.save(product);
	}

	// This method will delete product from DB as per input product id provided.
	@Override
	public void deleteProduct(Integer productId) throws ProductException {
		// If product id provided exists in DB then fetching corresponding product or
		// else throwing Product Exception.
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductException("Product not found. Please try again with valid product id."));

		// Deleting product from DB.
		productRepository.delete(product);
	}

	// This method will fetch product details from DB as per input product id
	// provided.
	@Override
	public Product getProduct(Integer productId) throws ProductException {
		// If product id provided exists in DB then fetching corresponding product or
		// else throwing Product Exception.
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductException("Product Not Found. Please try again with valid product id."));
		return product;
	}

	// This method will fetch all products from DB and then returns them.
	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	// This method will fetch all products from DB and sort them by product category
	// and then returns them.
	@Override
	public List<Product> sortProductByCategory() {
		// Comparator to sort List based on product category
		Comparator<Product> sortByProductCategory = new Comparator<Product>() {
			@Override
			public int compare(Product product1, Product product2) {
				return productRepository.getProductCategory(product1.getId())
						.compareTo(productRepository.getProductCategory(product2.getId()));
			}
		};

		// Fetching and sorting all orders from DB
		List<Product> productList = productRepository.findAll();
		Collections.sort(productList, sortByProductCategory);

		return productList;
	}
}
