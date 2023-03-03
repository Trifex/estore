package com.estore.api.estoreapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cart {
    
    private final static String STRING_FORMAT = "Shopping Cart [username = %s, items = %s, total = $%d";

    @JsonProperty("username") private String username;
    @JsonProperty("items") private ArrayList<Product> items;
    @JsonProperty("total") private double total;

    public Cart(@JsonProperty("username") String username) {
        this.username = username;
        this.items = new ArrayList<Product>();
        this.total = 0.0;
    }

    public String getUsername() {
        return this.username;
    }

    public ArrayList<Product> getItems() {
        return this.items;
    }

    public double getTotal() {
        return this.total;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setItems(ArrayList<Product> items) {
        this.items = items;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addToCart(Product product) {
        synchronized(items) {
            this.items.add(product);
            this.total += Math.round(product.getPrice() * 100.0) / 100.0;
            this.total = Math.round(total * 100.0) / 100.0;
        }
    }

    public void removeFromCart(Product product) {
        synchronized(items) {
            for(Product item : this.items) {
                if(item.getName().equals(product.getName())) {
                    this.items.remove(item);
                    this.total -= Math.round(product.getPrice() * 100.0) / 100.0;
                    this.total = Math.round(total * 100.0) / 100.0;
                    break;
                }
            }
        }
    }

    public void checkout() {
        this.items.clear();
        this.total = 0.0;
    }

    public void applyCoupon(Coupon coupon){
        synchronized(items){
            double totalCost = 0;
            for (Product item : this.items){
                String product_name = item.getName();
                if(coupon.containsProduct(product_name)){
                    totalCost += item.getPrice();
                }
            }
            this.total -= Math.round((totalCost * (Double.valueOf(coupon.getDiscount()) / 100.0)) * 100.0) / 100.0;
            this.total = Math.round(total * 100.0) / 100.0;
        }
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, this.username, this.items, this.total);
    }
}
