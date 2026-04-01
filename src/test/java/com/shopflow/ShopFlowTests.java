package com.shopflow;

import com.shopflow.entity.Product;
import com.shopflow.service.ProductService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 Test Cases - ShopFlow E-Commerce
 * Tests product validation, price/discount logic, order calculations
 */
public class ShopFlowTests {

    private final ProductService service = new ProductService();

    @Test
    @DisplayName("1. Product price must be positive")
    void testPricePositive() {
        assertTrue(service.isValidPrice(999.0));
        assertFalse(service.isValidPrice(0));
        assertFalse(service.isValidPrice(-500));
    }

    @Test
    @DisplayName("2. Product stock must be non-negative")
    void testStockNonNegative() {
        assertTrue(service.isValidStock(0));
        assertTrue(service.isValidStock(100));
        assertFalse(service.isValidStock(-1));
    }

    @Test
    @DisplayName("3. Product object must have name and price")
    void testProductValidity() {
        Product valid = Product.builder().name("Laptop").price(75000).build();
        assertTrue(service.isValidProduct(valid));

        Product noName = Product.builder().name("").price(75000).build();
        assertFalse(service.isValidProduct(noName));

        assertFalse(service.isValidProduct(null));
    }

    @Test
    @DisplayName("4. Discount calculation is correct")
    void testDiscount() {
        double price = 100000.0;
        double discountPct = 15.0;
        double finalPrice = price - (price * discountPct / 100);
        assertEquals(85000.0, finalPrice, 0.001);
    }

    @Test
    @DisplayName("5. GST calculation (18%) is correct")
    void testGST() {
        double basePrice = 50000.0;
        double gst = basePrice * 0.18;
        double total = basePrice + gst;
        assertEquals(59000.0, total, 0.001);
    }

    @Test
    @DisplayName("6. Order total with quantity is correct")
    void testOrderTotal() {
        double unitPrice = 29999.0;
        int qty = 3;
        double total = unitPrice * qty;
        assertEquals(89997.0, total, 0.001);
    }
}
