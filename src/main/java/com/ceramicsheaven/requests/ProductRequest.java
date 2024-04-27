package com.ceramicsheaven.requests;

import com.ceramicsheaven.model.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ProductRequest {
    private String title;
    private String description;
    private Integer price;
    private Integer discountPrice;
    private Integer discountPercent;
    private Integer qtyPerBox;
    private String brand;
    private String color;
//    private Integer thickness;
//    private Integer waterAbsorption;
    private Set<Size> sizes = new HashSet<>();
    private String imageUrl;
    private String categoryName;
    private LocalDateTime createdAt;

    public ProductRequest() {
    }

    public ProductRequest(String title, String description, Integer price, Integer discountPrice, Integer discountPercent, Integer qtyPerBox, String brand, String color, Set<Size> sizes, String imageUrl, String categoryName, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.qtyPerBox = qtyPerBox;
        this.brand = brand;
        this.color = color;
//        this.thickness = thickness;
//        this.waterAbsorption = waterAbsorption;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Integer getQtyPerBox() {
        return qtyPerBox;
    }

    public void setQtyPerBox(Integer qtyPerBox) {
        this.qtyPerBox = qtyPerBox;
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

//    public Integer getThickness() {
//        return thickness;
//    }
//
//    public void setThickness(Integer thickness) {
//        this.thickness = thickness;
//    }
//
//    public Integer getWaterAbsorption() {
//        return waterAbsorption;
//    }
//
//    public void setWaterAbsorption(Integer waterAbsorption) {
//        this.waterAbsorption = waterAbsorption;
//    }

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

    @Override
    public String toString() {
        return "CreateProductRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", discountPercent=" + discountPercent +
                ", qtyPerBox=" + qtyPerBox +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
//                ", thickness=" + thickness +
//                ", waterAbsorption=" + waterAbsorption +
                ", sizes=" + sizes +
                ", imageUrl='" + imageUrl + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
