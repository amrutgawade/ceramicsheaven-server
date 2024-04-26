package com.ceramicsheaven.responses;

public class AdminOrdersAndUsers {
    private Long totalSales;
    private Long pendingOrders;
    private Long totalUsers;

    public AdminOrdersAndUsers() {
    }

    public AdminOrdersAndUsers(Long totalSales, Long pendingOrders, Long totalUsers) {
        this.totalSales = totalSales;
        this.pendingOrders = pendingOrders;
        this.totalUsers = totalUsers;
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
}
