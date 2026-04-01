package com.shopflow.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String productName;

    private int quantity;
    private double totalPrice;
    private String address;

    @Builder.Default
    private String status = "PENDING";

    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now();
}
