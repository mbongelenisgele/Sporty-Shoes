package com.simplilearn.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PRODUCTS")
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String productName;
	private String productDescription;
	private Double unitPrice;
	private String manufacturer;
	private Integer unitsInStock;

	@OneToMany(targetEntity = Order.class,cascade = CascadeType.ALL)
    @JoinColumn(name ="product_fk",referencedColumnName = "id")
	private Set<Order> orders = new HashSet<>();
	
	public Set<Order> addOrder(Order order){
		this.orders.add(order);
		return this.orders;
	}
	
	public Set<Order> removeOrder(Order order){
		this.orders.remove(order);
		return this.orders;
	}
	
}
