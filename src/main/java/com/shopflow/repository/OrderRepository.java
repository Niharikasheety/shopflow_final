package com.shopflow.repository;

import com.shopflow.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmailIgnoreCase(String email);
    List<Order> findByStatus(String status);
    long countByStatus(String status);
}
