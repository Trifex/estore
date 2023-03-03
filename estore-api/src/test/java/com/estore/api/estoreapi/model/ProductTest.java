package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag ("Model-tier")
public class ProductTest {
    @Test
    public void testCreateProduct() throws IOException{
        Product product = new Product(1, "Flower Test", 4, "A flower for testing", 9.99);
        String result = product.toString();
        String expected = "Product [id = 1, name = Flower Test, copies = 4, description = A flower for testing, price = 9.990000]";
        assertEquals(expected, result);
    }
    @Test
    public void  testGetCopies() throws IOException{
        Product product = new Product(1, "Flower Test", 4, "A flower for testing", 9.99);
        int result = product.getCopies();
        int expected = 4;
        assertEquals(expected, result);
    }
    @Test
    public void  testGetDescription() throws IOException{
        Product product = new Product(1, "Flower Test", 4, "A flower for testing", 9.99);
        String result = product.getDescription();
        String expected = "A flower for testing";
        assertEquals(expected, result);
    }
    @Test
    public void testGetId() throws IOException{
        Product product = new Product(1, "Flower Test", 4, "A flower for testing", 9.99);
        int result = product.getId();
        int expected = 1;
        assertEquals(expected, result);
    }
    @Test
    public void  testGetName() throws IOException{
        Product product = new Product(1, "Flower Test", 4, "A flower for testing", 9.99);
        String result = product.getName();
        String expected = "Flower Test";
        assertEquals(expected, result);
    }
    @Test
    public void testGetPrice() throws IOException{
        Product product = new Product(1, "Flower Test", 4, "A flower for testing", 9.99);
        double result = product.getPrice();
        double expected = 9.99;
        assertEquals(expected, result);
    }
    @Test
    public void testSetCopies() throws IOException{
        Product product = new Product(1, "Flower Test", 4, "A flower for testing", 9.99);
        product.setCopies(5);
        int result = product.getCopies();
        int expected = 5;
        assertEquals(expected, result);
    }
    @Test
    public void testSetId() throws IOException{
        Product product = new Product(1, "Flower Test", 4, "A flower for testing", 9.99);
        product.setId(5);
        int result = product.getId();
        int expected = 5;
        assertEquals(expected, result);
    }
    @Test
    public void testSetPrice() throws IOException{
        Product product = new Product(1, "Flower Test", 4, "A flower for testing", 9.99);
        product.setPrice(5.99);
        double result = product.getPrice();
        double expected = 5.99;
        assertEquals(expected, result);
    }

    @Test
    public void testEquals() throws IOException {
        Product product1 = new Product(1, "Flower Test", 4, "A flower for testing", 9.99);
        Product product2 = new Product(2, "Flower Test", 4, "A flower for testing", 9.99);

        boolean equal = product1.equals(product2);
        assertTrue(equal);

        equal = product1.equals("Flower Test");
        assertFalse(equal);
    }
}