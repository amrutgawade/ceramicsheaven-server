package com.ceramicsheaven.services;


import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.model.*;
import com.ceramicsheaven.repositories.CartItemRepository;
import com.ceramicsheaven.repositories.CartRepository;
import com.ceramicsheaven.requests.AddItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplementation implements CartService{

	private CartRepository cartRepository;
	private CartItemService cartItemService;
	private ProductService productService;

	private CartItemRepository cartItemRepository;
	
	
	@Autowired
	public CartServiceImplementation(CartRepository cartRepository, CartItemService cartItemService,ProductService productService, CartItemRepository cartItemRepository) {
		this.cartRepository = cartRepository;
		this.cartItemService = cartItemService;
		this.productService = productService;
		this.cartItemRepository = cartItemRepository;
	}

	@Override
	public Cart CreateCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart = cartRepository.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());

		CartItems isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

		if (isPresent==null) {
			CartItems cartItem = new CartItems();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);

//			String[] dimensions = req.getSize().split("X");
//			if (dimensions.length != 2) {
//				throw new ProductException("Invalid size format");
//			}
//			int width = Integer.parseInt(dimensions[0].trim());
//			int height = Integer.parseInt(dimensions[1].trim());
//
//			for (Size size : product.getSizes()) {
//				if (size.getWidth() == width && size.getHeight() == height) {
//					size.setQuantity(size.getQuantity() - req.getQuantity());
//				}
//			}

			Integer price = req.getQuantity()*product.getPrice();
			System.out.println(price);
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

//			CartItems createdCartItems = cartItemService.creaCartItems(cartItem);
			cartItemRepository.save(cartItem);
			cart.getCartItems().add(cartItem);
			cart.setDiscount(price-cartItem.getDiscountedPrice());
			return "Item Add To Cart";
		}else{
			Integer price = req.getQuantity()*product.getPrice();
			isPresent.setPrice(isPresent.getPrice()+price);
			isPresent.setDiscountedPrice(isPresent.getDiscountedPrice()+isPresent.getProduct().getDiscountedPrice()*isPresent.getQuantity());
			isPresent.setQuantity(isPresent.getQuantity()+req.getQuantity());
//			CartItems createdCartItems = cartItemService.creaCartItems(cartItem);
			cartItemRepository.save(isPresent);
			return "Item Updated To Cart";
		}

	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);

		Integer totalPrice=0;
		Integer totalDiscountedPrice=0;
		Integer totalItems=0;

		for(CartItems cartItem: cart.getCartItems()) {
			totalPrice += cartItem.getPrice();
			totalDiscountedPrice += cartItem.getDiscountedPrice();
			totalItems+=1;
		}


		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItems);
		cart.setDiscount(totalPrice-totalDiscountedPrice);
		cart.setTotalPrice(totalPrice+50);

		return cartRepository.save(cart);
	}
}
