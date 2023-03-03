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

import com.estore.api.estoreapi.model.Coupon;
import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class InventoryFileDAOTest {
    InventoryFileDAO inventoryFileDAO;
    ObjectMapper mockObjectMapper;

    private Product[] testProducts = {
        new Product(1, "test1", 1, "test1", 10),
        new Product(2, "test2", 2, "test2", 20),
        new Product(3, "test3", 3, "test3", 30)
    };

    @BeforeEach
    public void setupCouponFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the coupon array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Product[].class))
                .thenReturn(testProducts);

        inventoryFileDAO = new InventoryFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetProductsArray() throws IOException {
        Product[] result1 = inventoryFileDAO.findProducts(null);
        Product[] result2 = inventoryFileDAO.findProducts("test");
        Product[] result3 = inventoryFileDAO.findProducts("test_notfound");

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);
    }

    @Test
    public void testUpdateProduct() throws IOException {
        // Setup
        Product product = new Product(1, "test1", 1, "test1", 20);

        // Invoke
        Product result = inventoryFileDAO.updateProduct(product);

        // Analyze
        assertNotNull(result);

        Product actual = inventoryFileDAO.getProduct(product.getId());
        assertEquals(product, actual);
    }

    @Test
    public void testUpdateProductNotFound() throws IOException {
        // Setup
        Product product = new Product(4, "test4", 1, "test4", 20);

        // Invoke
        Product result = inventoryFileDAO.updateProduct(product);

        // Analyze
        assertNull(result);
    }

    @Test
    public void testDeleteProduct() throws IOException {
        // Invoke
        boolean result = inventoryFileDAO.deleteProduct(1);

        // Analyze
        assertTrue(result);
    }

    @Test
    public void testDeleteProductNotFound() throws IOException {
        // Invoke
        boolean result = inventoryFileDAO.deleteProduct(4);

        // Analyze
        assertFalse(result);
    }
}
