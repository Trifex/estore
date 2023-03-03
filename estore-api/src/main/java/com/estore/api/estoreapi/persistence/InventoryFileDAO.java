package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class InventoryFileDAO implements InventoryDAO{

    Map<Integer, Product> products;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    public InventoryFileDAO(@Value("${products.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private static synchronized int nextId() {
        return nextId;
    }

    private Product[] getProductsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Product> productArrayList = new ArrayList<>();

        for (Product product : products.values()) {
            if (containsText == null || product.getName().contains(containsText)) {
                productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    private boolean save() throws IOException {
        Product[] productsArray = getInventory();
        objectMapper.writeValue(new File(filename),productsArray);
        load();
        return true;
    }

    private boolean load() throws IOException {
        products = new TreeMap<>();
        nextId = 0;
        Product[] productArray = objectMapper.readValue(new File(filename),Product[].class);

        for (Product product : productArray) {
            products.put(product.getId(), product);
        }

        int next = products.size() + 1; // The default next product id when we load is the end id + 1

        // Loop through all possible integers and if there is one missing, set that to be the next product added
        for(int i = 1; i <= products.size(); i++) {
            if(!products.keySet().contains(i)) {
                next = i;
                break;
            }
        }

        nextId = next;

        return true;
    }

    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(products){
            Product newProduct = new Product(nextId(), product.getName(), product.getCopies(), product.getDescription(), product.getPrice());
            products.put(newProduct.getId(), newProduct);
            save();
            return newProduct;
        }
    }

    @Override
    public Product[] getInventory() throws IOException {
        synchronized(products) {
            return getProductsArray(null);
        }
    }

    @Override
    public Product[] findProducts(String containsText) throws IOException {
        synchronized(products){
            return getProductsArray(containsText);
        }
    }

    @Override
    public Product getProduct(int id) throws IOException {
        return products.get(id); // Returns null if no product found
    }

    @Override
    public Product updateProduct(Product product) throws IOException {
        Product updated = products.replace(product.getId(), product) != null ? product : null; // Returns null if no product found
        save();
        return updated;
    }

    @Override
    public boolean deleteProduct(int id) throws IOException {
        boolean status = products.remove(id) != null;
        if(status)
            nextId = id;
        save();
        return status;
    }
}