package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Coupon;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.CartDAO;
import com.estore.api.estoreapi.persistence.CouponDAO;
import com.estore.api.estoreapi.persistence.InventoryDAO;

@Tag("Controller-tier")
public class CartControllerTest {
    private CartController cartController;
    private CartDAO mockCartDAO;
    private CouponDAO mockCouponDAO;
    private InventoryDAO mockInventoryDAO;

    private ArrayList<String> testProductNames = new ArrayList<>(List.of(
        "Bag of Fertilizer",
        "Bag of Mulch",
        "Bag of Soil",
        "Bag of Grass Seed"
    ));
    
    @BeforeEach
    public void setupCartController() {
        mockCartDAO = mock(CartDAO.class);
        mockInventoryDAO = mock(InventoryDAO.class);
        mockCouponDAO = mock(CouponDAO.class);
        cartController = new CartController(mockCartDAO, mockInventoryDAO, mockCouponDAO);
    }

    @Test
    public void testGetCart() throws IOException {
        Cart cart = new Cart("testuser");

        when(mockCartDAO.getCart(cart.getUsername())).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.getCart(cart.getUsername());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testGetCartNotFound() throws IOException {
        String username = "testuser";

        when(mockCartDAO.getCart(username)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.getCart(username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddToCart() throws IOException {
        Cart cart = new Cart("testuser");
        Product product = new Product(99, "testproduct", 5, "testdescription", 10);

        when(mockInventoryDAO.getProduct(product.getId())).thenReturn(product);
        when(mockCartDAO.addToCart(cart.getUsername(), product)).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.addToCart(cart.getUsername(), product.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());

        when(mockInventoryDAO.getProduct(product.getId())).thenReturn(null);
        response = cartController.addToCart(cart.getUsername(), product.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCart() throws IOException {
        Cart cart = new Cart("testuser");
        Product product = new Product(99, "testproduct", 5, "testdescription", 10);

        when(mockInventoryDAO.getProduct(product.getId())).thenReturn(product);
        when(mockCartDAO.removeFromCart(cart.getUsername(), product)).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.removeFromCart(cart.getUsername(), product.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());

        when(mockInventoryDAO.getProduct(product.getId())).thenReturn(null);
        response = cartController.removeFromCart(cart.getUsername(), product.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCheckout() throws IOException {
        Cart cart = new Cart("testuser");
        
        when(mockCartDAO.checkout(cart.getUsername())).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.checkout(cart.getUsername());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testCheckoutNotFound() throws IOException {
        Cart cart = new Cart("testuser");
        
        when(mockCartDAO.checkout(cart.getUsername())).thenReturn(null);

        ResponseEntity<Cart> response = cartController.checkout(cart.getUsername());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testApplyCoupon() throws IOException {
        Cart cart = new Cart("username");
        Coupon coupon = new Coupon(1, "test", 20, testProductNames);
        
        when(mockCouponDAO.getCoupon(coupon.getName())).thenReturn(coupon);
        when(mockCartDAO.applyCoupon(coupon, cart.getUsername())).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.applyCoupon(cart.getUsername(), "test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testApplyCouponNotFound() throws IOException {
        Cart cart = new Cart("testuser");
        Coupon coupon = new Coupon(1, "test", 20, testProductNames);
        
        when(mockCartDAO.applyCoupon(coupon, cart.getUsername())).thenReturn(null);

        ResponseEntity<Cart> response = cartController.applyCoupon(cart.getUsername(), "test");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}