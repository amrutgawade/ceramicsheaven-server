package com.ceramicsheaven.repositories;

import com.ceramicsheaven.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> getUsersOrders(@Param("userId") Long userId);

    @Query("SELECT COUNT(*) FROM Order o WHERE o.orderStatus IN ('PLACED', 'CONFIRMED', 'SHIPPED')")
    Long pendingOrders();



}
