package com.simplilearn.entity;

import javax.persistence.*;

import com.simplilearn.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_TABLE")
public class User {
	@Id
	private String userName;
	private String userFirstName;
	private String userLastName;
	private String userPassword;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	private Set<Role> role;
	
	@OneToMany(targetEntity = Order.class,cascade = CascadeType.ALL)
    @JoinColumn(name ="username_fk",referencedColumnName = "userName")
	private Set<Order> orders = new HashSet<Order>();
	
	public Set<Order> addOrder(Order order){
		this.orders.add(order);
		return this.orders;
	}
	
	public Set<Order> removeOrder(Order order){
		this.orders.remove(order);
		return this.orders;
	}

}
