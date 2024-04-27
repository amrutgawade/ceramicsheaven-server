package com.ceramicsheaven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "discription")
	private String discription;

	@Column(name = "price")
	private Integer price;

	@Column(name = "discounted_price")
	private Integer discountedPrice;

	@Column(name = "discounted_percent")
	private Integer discountedPercent;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "brand")
	private String brand;

	@Column(name = "color")
	private String color;

	@Column(name = "qty_per_box")
	private Integer qtyPerBox;

	@Embedded
	@ElementCollection
	@CollectionTable(name = "product_size", joinColumns = @JoinColumn(name = "product_id"))
	private Set<Size> sizes = new HashSet<>();

	@Column(name = "image_url")
	private String imageUrl;

	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Rating> rating = new ArrayList<>();

	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Review> review = new ArrayList<>();

	@Column(name = "num_rating")
	private Integer numRating;

	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

	private LocalDateTime createdAt;


	public Product() {
	}

	public Product(Long id, String title, String discription, Integer price, Integer discountedPrice, Integer discountedPercent, Integer quantity, String brand, String color, Integer qtyPerBox, Set<Size> sizes, String imageUrl, List<Rating> rating, List<Review> review, Integer numRating, Category category, LocalDateTime createdAt) {
		this.id = id;
		this.title = title;
		this.discription = discription;
		this.price = price;
		this.discountedPrice = discountedPrice;
		this.discountedPercent = discountedPercent;
		this.quantity = quantity;
		this.brand = brand;
		this.color = color;
		this.qtyPerBox = qtyPerBox;
		this.sizes = sizes;
		this.imageUrl = imageUrl;
		this.rating = rating;
		this.review = review;
		this.numRating = numRating;
		this.category = category;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(Integer discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public Integer getDiscountedPercent() {
		return discountedPercent;
	}

	public void setDiscountedPercent(Integer discountedPercent) {
		this.discountedPercent = discountedPercent;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getQtyPerBox() {
		return qtyPerBox;
	}

	public void setQtyPerBox(Integer qtyPerBox) {
		this.qtyPerBox = qtyPerBox;
	}

	public Set<Size> getSizes() {
		return sizes;
	}

	public void setSizes(Set<Size> sizes) {
		this.sizes = sizes;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	public Integer getNumRating() {
		return numRating;
	}

	public void setNumRating(Integer numRating) {
		this.numRating = numRating;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
