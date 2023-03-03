package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryDAO;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("products")
public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private InventoryDAO inventoryDAO;

    public InventoryController(InventoryDAO inventoryDAO){
        this.inventoryDAO = inventoryDAO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        LOG.info("GET /products/" + id);
        try {
            Product product = inventoryDAO.getProduct(id);
            if (product != null)
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Product[]> searchProducts(@RequestParam String name) {
        LOG.info("GET /products/?name="+name);
        try{
            Product[] products = inventoryDAO.findProducts(name);
            return new ResponseEntity<Product[]>(products, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Product[]> getInventory() {
        LOG.info("GET /products");
        try {
            Product[] inventory = inventoryDAO.getInventory();
            return new ResponseEntity<Product[]>(inventory, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        LOG.info("POST /product " + product);
        try{
            Product createdProduct = inventoryDAO.createProduct(product);
            if(createdProduct == null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            String productName = createdProduct.getName();
            ResponseEntity<Product[]> returnedProducts = searchProducts(productName);
            if(returnedProducts == null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<Product>(createdProduct, HttpStatus.CREATED);
        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        LOG.info("PUT /products " + product);
        try {
            Product found = inventoryDAO.updateProduct(product);
            if(found != null) {     
                return new ResponseEntity<Product>(found, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        LOG.info("DELETE /product/" + id);

        try {
            boolean found = inventoryDAO.deleteProduct(id);

            if(found) {     
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}