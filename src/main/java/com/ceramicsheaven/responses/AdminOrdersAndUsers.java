package com.ceramicsheaven.responses;

public class AdminOrdersAndUsers {
    private Long totalSales;
    private Long pendingOrders;
    private Long totalUsers;

    private Long totalProducts;

    public AdminOrdersAndUsers() {
    }
    public AdminOrdersAndUsers(Long totalSales, Long pendingOrders, Long totalUsers, Long totalProducts) {
        this.totalSales = totalSales;
        this.pendingOrders = pendingOrders;
        this.totalUsers = totalUsers;
        this.totalProducts = totalProducts;
    }

    public Long getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Long totalSales) {
        this.totalSales = totalSales;
    }

    public Long getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(Long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }
}
