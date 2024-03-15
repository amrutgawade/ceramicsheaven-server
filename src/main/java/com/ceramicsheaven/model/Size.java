package com.ceramicsheaven.model;

public class Size {

    private Long width;
    private Long height;

    private Integer quantity;

    public Size() {
    }

    public Size(Long width, Long height, Integer quantity) {
        this.width = width;
        this.height = height;
        this.quantity = quantity;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Size{" +
                "width=" + width +
                ", height=" + height +
                ", quantity=" + quantity +
                '}';
    }
}
