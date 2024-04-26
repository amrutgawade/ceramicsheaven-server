package com.ceramicsheaven.services;

import com.ceramicsheaven.model.Address;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.exceptions.OrderException;
import com.ceramicsheaven.model.Order;
import com.ceramicsheaven.responses.AdminOrdersAndUsers;

import java.util.List;

public interface OrderService {
	
	public Order createOrder(User user, Address shippingAddress);
	
	public Order findOrderById(Long OrderId)throws OrderException;
	
	public List<Order> usersOrderHistory(Long userId);
	
	public Order placedOrder (Long orderId) throws OrderException;
	
	public Order confirmedOrder (Long orderId) throws OrderException;
	
	public Order shippedOrder (Long orderId) throws OrderException;
	
	public Order deliveredOrder (Long orderId) throws OrderException;
	
	public Order cancledOrder (Long orderId) throws OrderException;
	
	public List<Order> getAllOrders();
	
	public void deleteOrder(Long orderId) throws OrderException;

	public AdminOrdersAndUsers getTotalData();
}
