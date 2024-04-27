package com.ceramicsheaven.requests;

import com.ceramicsheaven.model.Category;
import com.ceramicsheaven.model.Rating;
import com.ceramicsheaven.model.Review;
import com.ceramicsheaven.model.Size;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductUpdateRequest {

    private String title;

    private String discription;

    private Integer price;

    private Integer discountedPrice;

    private Integer discountedPercent;

    private Integer quantity;

    private String brand;

    private String color;

    private Integer qtyPerBox;

    private Set<Size> sizes = new HashSet<>();

    private String imageUrl;

    private String categoryName;

    private LocalDateTime createdAt;

    public ProductUpdateRequest() {
    }

    public ProductUpdateRequest(String title, String discription, Integer price, Integer discountedPrice, Integer discountedPercent, Integer quantity, String brand, String color, Integer qtyPerBox, Set<Size> sizes, String imageUrl, String categoryName, LocalDateTime createdAt) {
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
        this.categoryName = categoryName;
        this.createdAt = createdAt;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
