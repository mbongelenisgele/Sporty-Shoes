package com.simplilearn.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

	private Integer orderId;
	private String orderTrackingNumber;
	@NotNull(message = "Total quantity of products cannot be null")
	@Positive(message = "Total quantity should be positive number.")
	private int totalQuantity;
	@NotNull(message = "Billing address cannot be null")
	private String address;
	@NotNull(message = "Product id cannot be null")
	@Positive(message = "Product id should be positive number.")
	private Integer productId;
	@NotNull(message = "Username cannot be null")
	private String userName;
}
