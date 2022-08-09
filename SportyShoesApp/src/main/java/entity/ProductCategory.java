package com.simplilearn.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
@Entity(name = "PRODUCT_CATEGORY")
@Table(name = "PRODUCT_CATEGORY")
public class ProductCategory {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Integer id;
	private String categoryName;
	private String categoryDescription;
	
	@OneToMany(targetEntity = Product.class,cascade = CascadeType.ALL)
    @JoinColumn(name ="prod_category_fk",referencedColumnName = "categoryName")
	private Set<Product> products = new HashSet<>();
	
	public Set<Product> addProduct(Product product){
		this.products.add(product);
		return this.products;
	}
	
	public Set<Product> removeProduct(Product product){
		this.products.remove(product);
		return this.products;
	}
}
