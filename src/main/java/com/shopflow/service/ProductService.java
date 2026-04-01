package com.shopflow.service;

import com.shopflow.entity.Product;
import com.shopflow.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public List<Product> getAll() { return repo.findByActiveTrue(); }

    public Optional<Product> getById(Long id) { return repo.findById(id); }

    public Product save(Product p) { return repo.save(p); }

    public Product update(Long id, Product updated) {
        return repo.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setDescription(updated.getDescription());
            p.setPrice(updated.getPrice());
            p.setStock(updated.getStock());
            p.setCategory(updated.getCategory());
            p.setImageUrl(updated.getImageUrl());
            return repo.save(p);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void delete(Long id) {
        repo.findById(id).ifPresent(p -> { p.setActive(false); repo.save(p); });
    }

    public List<Product> search(String name) {
        return repo.findByNameContainingIgnoreCaseAndActiveTrue(name);
    }

    // Validation helpers used by JUnit
    public boolean isValidPrice(double price) { return price > 0; }
    public boolean isValidStock(int stock)     { return stock >= 0; }
    public boolean isValidProduct(Product p)   {
        return p != null && p.getName() != null && !p.getName().isBlank() && p.getPrice() > 0;
    }
}
