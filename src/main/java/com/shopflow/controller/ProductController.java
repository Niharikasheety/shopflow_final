package com.shopflow.controller;

import com.shopflow.entity.Product;
import com.shopflow.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product p) { return service.save(p); }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product p) {
        try { return ResponseEntity.ok(service.update(id, p)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String q) { return service.search(q); }
}
