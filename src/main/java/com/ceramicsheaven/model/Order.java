package com.ceramicsheaven.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
	
	 @Id
//	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 @ManyToOne(cascade = CascadeType.ALL)
	 private User user;

	 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true)
	 private List<OrderItem> orderItems = new ArrayList<>();

	 private LocalDateTime orderDate;
	 private LocalDateTime deliveryDate;

	 @OneToOne
	 private Address shippingAddresses;

	 @Embedded
	 private PaymentDetails paymentDetails = new PaymentDetails();
	 private Double totalPrice;
	 private Integer totalDiscountedPrice;
	 private Integer discount;
	 private String orderStatus;
	 private Integer totalItem;
	 private LocalDateTime createdAt;

	public Order() {
	}

	public Order(Long id,User user, List<OrderItem> orderItems, LocalDateTime orderDate,
				 LocalDateTime deliveryDate, Address shippingAddresses, PaymentDetails paymentDetails,
				 Double totalPrice, Integer totalDiscountedPrice, Integer discount, String orderStatus, Integer totalItem,
				 LocalDateTime createdAt) {
		this.user = user;
		this.id=id;
		this.orderItems = orderItems;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.shippingAddresses = shippingAddresses;
		this.paymentDetails = paymentDetails;
		this.totalPrice = totalPrice;
		this.totalDiscountedPrice = totalDiscountedPrice;
		this.discount = discount;
		this.orderStatus = orderStatus;
		this.totalItem = totalItem;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Address getShippingAddresses() {
		return shippingAddresses;
	}

	public void setShippingAddresses(Address shippingAddresses) {
		this.shippingAddresses = shippingAddresses;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getTotalDiscountedPrice() {
		return totalDiscountedPrice;
	}

	public void setTotalDiscountedPrice(Integer totalDiscountedPrice) {
		this.totalDiscountedPrice = totalDiscountedPrice;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", user=" + user +
				", orderItems=" + orderItems +
				", orderDate=" + orderDate +
				", deliveryDate=" + deliveryDate +
				", shippingAddresses=" + shippingAddresses +
				", paymentDetails=" + paymentDetails +
				", totalPrice=" + totalPrice +
				", totalDiscountedPrice=" + totalDiscountedPrice +
				", discount=" + discount +
				", orderStatus='" + orderStatus + '\'' +
				", totalItem=" + totalItem +
				", createdAt=" + createdAt +
				'}';
	}
}