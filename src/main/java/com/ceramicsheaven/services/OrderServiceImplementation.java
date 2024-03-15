package com.ceramicsheaven.services;


import com.ceramicsheaven.model.*;
import com.ceramicsheaven.repositories.AddressRepository;
import com.ceramicsheaven.repositories.OrderItemRepository;
import com.ceramicsheaven.repositories.OrderRepository;
import com.ceramicsheaven.repositories.UserRepository;
import com.ceramicsheaven.exceptions.OrderException;
import com.ceramicsheaven.model.*;
import com.ceramicsheaven.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService{

	private OrderRepository orderRepository;

	private CartService cartService;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
//	private OrderItemService orderItemService;
	private OrderItemRepository orderItemRepository;

	@Autowired
	public OrderServiceImplementation(OrderRepository orderRepository, CartService cartService, AddressRepository addressRepository, UserRepository userRepository, OrderItemRepository orderItemRepository) {
		this.orderRepository = orderRepository;
		this.cartService = cartService;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
//		this.orderItemService = orderItemService;
		this.orderItemRepository = orderItemRepository;
	}



	@Override
	public Order createOrder(User user, Address shippingAddress) {

		shippingAddress.setUser(user);
		Address address = addressRepository.save(shippingAddress);
		user.getAddress().add(address);
		userRepository.save(user);

		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItems cartItems:cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(cartItems.getPrice());
			orderItem.setProduct(cartItems.getProduct());
			orderItem.setQuantity(cartItems.getQuantity());
			orderItem.setSize(cartItems.getSize());
			orderItem.setUserId(cartItems.getUserId());
			orderItem.setDiscountedPrice(cartItems.getDiscountedPrice());
			System.out.println(orderItem.toString());

			OrderItem createdOrderItem = orderItemRepository.save(orderItem);
			System.out.println(createdOrderItem);
			orderItems.add(createdOrderItem);
		}

		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setTotalItem(orderItems.size());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setShippingAddresses(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setPaymentStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());

		Order savedOrder = orderRepository.save(createdOrder);
        for(OrderItem orderItem:orderItems){
			orderItem.setOrder(createdOrder);
			orderItemRepository.save(orderItem);
		}
		return savedOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> order = orderRepository.findById(orderId);

		if (order.isPresent())
			return order.get();
		throw new OrderException("Order does not exists");
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order> orders = orderRepository.getUsersOrders(userId);
		return orders;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setPaymentStatus("COMPLETED");
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");

		return order;
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");

		return order;
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");

		return order;
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return order;
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		orderRepository.deleteById(orderId);
		
	}

}
