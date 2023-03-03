package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Coupon;
import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class CartFileDAOTest {
    CartFileDAO cartFileDAO;
    ObjectMapper mockObjectMapper;

    private Cart[] testCarts = {
        new Cart("user1"),
        new Cart("user2"),
        new Cart("user3")
    };

    @BeforeEach
    public void setupCouponFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the coupon array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Cart[].class))
                .thenReturn(testCarts);

                cartFileDAO = new CartFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetShoppingCarts() throws IOException {
        Cart[] result = cartFileDAO.getAllCarts();

        for(int i = 0; i < result.length; i++) {
            assertEquals(testCarts[i], result[i]);
        }
    }

    @Test
    public void testAddToCart() throws IOException {
        // Setup
        Product product = new Product(1, "test1", 1, "test1", 20);

        // Invoke
        Cart result = cartFileDAO.addToCart("user1", product);

        // Analyze
        assertNotNull(result);
    }
}
