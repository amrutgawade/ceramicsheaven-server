package com.ceramicsheaven.services;


import com.ceramicsheaven.model.*;
import com.ceramicsheaven.repositories.AddressRepository;
import com.ceramicsheaven.repositories.OrderItemRepository;
import com.ceramicsheaven.repositories.OrderRepository;
import com.ceramicsheaven.repositories.UserRepository;
import com.ceramicsheaven.exceptions.OrderException;
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

	private OrderItemRepository orderItemRepository;

	@Autowired
	public OrderServiceImplementation(OrderRepository orderRepository, CartService cartService, AddressRepository addressRepository, UserRepository userRepository, OrderItemRepository orderItemRepository) {
		this.orderRepository = orderRepository;
		this.cartService = cartService;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
// this.orderItemService = orderItemService;
		this.orderItemRepository = orderItemRepository;
	}



	@Override
	public Order createOrder(User user, Address shippingAddress) {
		LocalDateTime localDateTime = LocalDateTime.now();

		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItems cartItems : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(cartItems.getPrice());
			orderItem.setProduct(cartItems.getProduct());
			orderItem.setQuantity(cartItems.getQuantity());
			orderItem.setSize(cartItems.getSize());
			orderItem.setUserId(cartItems.getUserId());
			orderItem.setDiscountedPrice(cartItems.getDiscountedPrice());

			orderItemRepository.save(orderItem);
			orderItems.add(orderItem);
		}

		// Generate a unique order_id
		Long orderId = generateUniqueOrderId();

		Order createdOrder = new Order();
		createdOrder.setId(orderId);
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setTotalItem(orderItems.size());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setShippingAddresses(shippingAddress);
		createdOrder.setOrderDate(localDateTime);
		createdOrder.setDeliveryDate(localDateTime.plusDays(7));
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setPaymentStatus("PENDING");
		createdOrder.getPaymentDetails().setPaymentMethod("CASH_ON_DELIVERY");
		createdOrder.setCreatedAt(LocalDateTime.now());
		orderRepository.save(createdOrder);

		for (OrderItem orderItem : orderItems) {
			orderItem.setOrder(createdOrder);
			orderItemRepository.save(orderItem);
		}
		return createdOrder;
	}

	// Method to generate a unique order_id
	private Long generateUniqueOrderId() {
		// Logic to generate a unique order_id, for example, you can use a UUID or a sequence
		// For simplicity, let's say you use the current timestamp as the order_id
		return System.currentTimeMillis();
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
		if (order==null){
			throw new OrderException("There is not available product");
		}
		order.setOrderStatus("CONFIRMED");

		return order;
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		if (order==null){
			throw new OrderException("There is not available product");
		}
		order.setOrderStatus("SHIPPED");

		return order;
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		if (order==null){
			throw new OrderException("There is not available product");
		}
		order.setOrderStatus("DELIVERED");

		return order;
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		if (order==null){
            throw new OrderException("There is not available product");
		}
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
		if (order==null){
			throw new OrderException("There is not available product");
		}
		orderRepository.deleteById(orderId);

	}

}
