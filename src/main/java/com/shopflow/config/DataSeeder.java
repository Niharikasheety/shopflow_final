package com.shopflow.config;

import com.shopflow.entity.Product;
import com.shopflow.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private ProductRepository repo;

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            repo.save(Product.builder().name("MacBook Pro 14").description("Apple M3 chip, 16GB RAM, 512GB SSD. Perfect for developers.").price(199999).stock(15).category("Laptops").imageUrl("https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400").build());
            repo.save(Product.builder().name("iPhone 15 Pro").description("Titanium design, A17 Pro chip, 256GB. The ultimate iPhone.").price(134999).stock(30).category("Phones").imageUrl("https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=400").build());
            repo.save(Product.builder().name("Sony WH-1000XM5").description("Industry-leading noise cancellation, 30hr battery life.").price(29999).stock(50).category("Audio").imageUrl("https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?w=400").build());
            repo.save(Product.builder().name("Samsung 4K Monitor").description("27-inch IPS display, 144Hz refresh, HDR600 support.").price(45999).stock(20).category("Monitors").imageUrl("https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=400").build());
            repo.save(Product.builder().name("iPad Air M2").description("10.9-inch Liquid Retina, M2 chip, 5G support.").price(74999).stock(25).category("Tablets").imageUrl("https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=400").build());
            repo.save(Product.builder().name("Logitech MX Master 3S").description("Ergonomic wireless mouse, 8K DPI, silent clicks.").price(9999).stock(80).category("Accessories").imageUrl("https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400").build());
            System.out.println("Sample products seeded successfully.");
        }
    }
}
