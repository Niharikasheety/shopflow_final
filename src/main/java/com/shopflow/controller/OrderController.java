package com.shopflow.controller;

import com.shopflow.entity.Order;
import com.shopflow.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping
    public List<Order> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Order place(@RequestBody Order o) { return service.place(o); }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try { return ResponseEntity.ok(service.updateStatus(id, status)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        Map<String, Object> s = new HashMap<>();
        s.put("total",     service.getAll().size());
        s.put("pending",   service.countByStatus("PENDING"));
        s.put("shipped",   service.countByStatus("SHIPPED"));
        s.put("delivered", service.countByStatus("DELIVERED"));
        return s;
    }
}
