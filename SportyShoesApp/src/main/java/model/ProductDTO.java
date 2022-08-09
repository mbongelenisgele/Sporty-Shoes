package com.simplilearn.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	private Integer productId;
	@NotNull(message = "Product name cannot be null")
	private String productName;
	@NotNull(message = "Product description cannot be null")
	private String productDescription;
	@NotNull(message = "Product category name cannot be null")
	private String productCategoryName;
	@NotNull(message = "Product category description cannot be null")
	private String productCategoryDescription;
	@NotNull(message = "Product manufacturer cannot be null")
	private String manufacturer;
	@NotNull(message = "Unit price for product cannot be null")
	@Positive(message = "Unit Price should be positive number.")
	private Double unitPrice;
	@NotNull(message = "Units in stock for product cannot be null")
	@Positive(message = "Units In Stock should be positive number.")
	private Integer unitsInStock;
	private List<OrderRequest> orderDTOList = new ArrayList<>();
}
