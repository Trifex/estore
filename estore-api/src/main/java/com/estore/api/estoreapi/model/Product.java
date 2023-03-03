package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    private static final String STRING_FORMAT = "Product [id = %d, name = %s, copies = %d, description = %s, price = %f]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("copies") private int copies;
    @JsonProperty("description") private String description;
    @JsonProperty("price") private double price;
    
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("copies") int copies, @JsonProperty("description") String description, @JsonProperty("price") double price){
        this.id = id;
        this.name = name;
        this.copies = copies;
        this.description = description;
        this.price = price;
    }

    public int getCopies() {
        return this.copies;
    }

    public String getDescription() {
        return this.description;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return price;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Product) {
            Product product = (Product) o;
            return this.name.equals(product.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, this.id, this.name, this.copies, this.description, this.price);
    }
}