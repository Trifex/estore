package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

public interface InventoryDAO {
    Product[] getInventory() throws IOException;
    Product[] findProducts(String containsText) throws IOException;
    Product getProduct(int id) throws IOException;
    Product createProduct(Product product) throws IOException;
    Product updateProduct(Product product) throws IOException;
    boolean deleteProduct(int id) throws IOException;
}