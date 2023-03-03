package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;

@Tag ("Model-tier")
public class CartTest {
    private ArrayList<String> testProductNames = new ArrayList<>(List.of(
        "Bag of Fertilizer",
        "Bag of Mulch",
        "Bag of Soil",
        "Bag of Grass Seed"
    ));

    @Test
    public void testApplyCoupon() throws IOException {
        Coupon coupon = new Coupon(1, "EMPLOYEE20", 20, testProductNames);
        Product product1 = new Product(1, "test", 1, "test", 10);
        Product product2 = new Product(2, "test2", 1, "test2", 10);
        coupon.addProduct("test");
        Cart cart = new Cart("username");
        cart.addToCart(product1);
        cart.addToCart(product2);

        cart.applyCoupon(coupon);
        double actual = cart.getTotal();

        assertEquals(18, actual);
    }

    @Test
    public void testRemoveFromCart() throws IOException {
        Cart cart = new Cart("username");
        Product product1 = new Product(1, "test", 1, "test", 10);
        Product product2 = new Product(2, "test2", 1, "test2", 10);
        cart.addToCart(product1);
        cart.addToCart(product2);
        
        cart.removeFromCart(product1);
        double actual = cart.getTotal();

        assertEquals(10, actual);

        // test removing a product that doesnt exist
        cart.removeFromCart(product1);
        actual = cart.getTotal();

        assertEquals(10, actual);
    }
}