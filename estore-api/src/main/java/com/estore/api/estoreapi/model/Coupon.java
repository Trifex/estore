package com.estore.api.estoreapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coupon {

    private static final String STRING_FORMAT = "Coupon [id = %d, name = %s, discount = %d, products = %s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("discount") private int discount;
    @JsonProperty("products") private ArrayList<String> products;
    
    public Coupon(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("discount") int discount, @JsonProperty("products") ArrayList<String> products){
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.products = products;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getDiscount() {
        return this.discount;
    }

    public ArrayList<String> getProducts() {
        return this.products;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void addProduct(String product) {
        if (!this.products.contains(product))
            this.products.add(product);
    }

    /**
     * 
     * @param product the product to remove the discount from
     * @return true if the product exists and has been removed, false if it was not in the list of products
     */
    public boolean removeProduct(String product) {
        // Since each ID should be unique, removing the first instance of the product int specified should remove it
        return this.products.remove(product);
    }

    public boolean containsProduct(String product) {
        return this.products.contains(product);
    }

    public int numProducts() {
        return this.products.size();
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, this.id, this.name, this.discount, this.products.toString());
    }
}