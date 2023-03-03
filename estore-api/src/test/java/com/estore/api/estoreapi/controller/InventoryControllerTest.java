package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryDAO;

@Tag("Controller-tier")
public class InventoryControllerTest {
    private InventoryController inventoryController;
    private InventoryDAO mockInventoryDAO;
    
    @BeforeEach
    public void setupProductController() {
        mockInventoryDAO = mock(InventoryDAO.class);
        inventoryController = new InventoryController(mockInventoryDAO);
    }

    @Test
    public void testCreateProduct() throws IOException{
        Product product = new Product(1, "Apple", 3, "Granny Smith", 1.0);
        //System.out.println(product);
        when(mockInventoryDAO.createProduct(product)).thenReturn(product);
        ResponseEntity<Product> response = inventoryController.createProduct(product);
        assertEquals((HttpStatus.CREATED), response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testCreateProductFailed() throws IOException{
        Product product = new Product(2, "Fig", 4, "Fruit", 4.2);
        when(mockInventoryDAO.createProduct(product)).thenReturn(null);
        ResponseEntity<Product> response = inventoryController.createProduct(product);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testGetProduct() throws IOException {  // getProduct may throw IOException
        // Setup
        Product product = new Product(99, "Orange", 5, "Orange (Fruit)", 5.0);
        // When the same id is passed in, our mock Product DAO will return the Product object
        when(mockInventoryDAO.getProduct(product.getId())).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(product.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testGetProductNotFound() throws Exception { // createProduct may throw IOException
        // Setup
        int productId = 99;
        // When the same id is passed in, our mock Product DAO will return null, simulating
        // no product found
        when(mockInventoryDAO.getProduct(productId)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(productId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testSearchProducts() throws IOException {
        // Setup
        String searchString = "Apple";
        Product[] inventory = new Product[2];
        inventory[0] = new Product(1, "Apple", 3, "Granny Smith", 5);
        inventory[1] = new Product(2, "Apple Pie", 4, "Dessert", 8.4);
        when(mockInventoryDAO.findProducts(searchString)).thenReturn(inventory);
        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(inventory,response.getBody());
    }

    @Test
    public void testGetInventory() throws IOException {
        // Setup
        Product[] inventory = new Product[2];
        inventory[0] = new Product(1, "Apple", 3, "Granny Smith", 6);
        inventory[1] = new Product(2, "Fig", 4, "Fruit", 7.7);
        when(mockInventoryDAO.getInventory()).thenReturn(inventory);
        ResponseEntity<Product[]> response = inventoryController.getInventory();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(inventory,response.getBody());
    }

    @Test
    public void testUpdateProduct() throws IOException { // updateProduct may throw IOException
        // Setup
        Product product = new Product(99, "Orange", 5, "Orange (Fruit)", 5.0);
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockInventoryDAO.updateProduct(product)).thenReturn(product);
        ResponseEntity<Product> response = inventoryController.updateProduct(product);
        product.setName("Pear");
        product.setDescription("Pear (Fruit)");

        // Invoke
        response = inventoryController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testUpdateProductFailed() throws IOException { // updateProduct may throw IOException
        // Setup
        Product product = new Product(99, "Orange", 5, "Orange (Fruit)", 5.0);
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockInventoryDAO.updateProduct(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = inventoryController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException { // deleteProduct may throw IOException
        // Setup
        int productId = 99;
        // when deleteProduct is called return true, simulating successful deletion
        when(mockInventoryDAO.deleteProduct(productId)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(productId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException { // deleteProduct may throw IOException
        // Setup
        int productId = 99;
        // when deleteProduct is called return false, simulating failed deletion
        when(mockInventoryDAO.deleteProduct(productId)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(productId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
}