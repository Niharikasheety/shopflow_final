package com.shopflow.service;

import com.shopflow.entity.Order;
import com.shopflow.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repo;

    public List<Order> getAll()                   { return repo.findAll(); }
    public Optional<Order> getById(Long id)        { return repo.findById(id); }
    public Order place(Order o)                    { return repo.save(o); }
    public List<Order> byEmail(String email)        { return repo.findByCustomerEmailIgnoreCase(email); }
    public long countByStatus(String status)        { return repo.countByStatus(status); }

    public Order updateStatus(Long id, String status) {
        return repo.findById(id).map(o -> { o.setStatus(status); return repo.save(o); })
                   .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void delete(Long id) { repo.deleteById(id); }
}
