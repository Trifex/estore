package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Coupon;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CartFileDAO implements CartDAO {
    
    Map<String, Cart> carts;
    private String filename;
    private ObjectMapper objectMapper;

    public CartFileDAO(@Value("${cart.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private Cart[] getShoppingCarts() {
        ArrayList<Cart> cartArrayList = new ArrayList<>();

        for(Cart cart : carts.values()) {
            cartArrayList.add(cart);
        }

        Cart[] cartArray = new Cart[cartArrayList.size()];
        cartArrayList.toArray(cartArray);
        return cartArray;
    }

    private boolean save() throws IOException {
        Cart[] cartsArray = getShoppingCarts();
        objectMapper.writeValue(new File(filename),cartsArray);
        load();
        return true; 
    }

    private boolean load() throws IOException {
        carts = new TreeMap<>();
        Cart[] cartArray = objectMapper.readValue(new File(filename),Cart[].class);
        for(Cart cart : cartArray) {
            carts.put(cart.getUsername(), cart);
        }
        return true;
    }

    @Override
    public Cart[] getAllCarts() {
        synchronized(carts) {
            return getShoppingCarts();
        }
    }

    @Override
    public Cart getCart(String username) throws IOException {
        synchronized(carts) {
            return carts.get(username);
        }
    }

    @Override
    public Cart createCart(String username) throws IOException {
        synchronized(carts) {
            Cart newCart = new Cart(username);
            carts.put(username, newCart);
            save();
            return newCart;
        }
    }

    @Override
    public Cart addToCart(String username, Product product) throws IOException {
        synchronized(carts) {
            if(this.carts.containsKey(username)) {
                Cart cart = carts.get(username);
                cart.addToCart(product);
                product.setCopies(product.getCopies() - 1);
                carts.put(username, cart);
                save();
                return cart;
            } else {
                Cart newCart = this.createCart(username);
                newCart.addToCart(product);
                product.setCopies(product.getCopies() - 1);
                carts.put(username, newCart);
                save();
                return newCart;
            }
        }
    }

    @Override
    public Cart removeFromCart(String username, Product product) throws IOException {
        synchronized(carts) {
            Cart cart = carts.get(username);
            cart.removeFromCart(product);
            product.setCopies(product.getCopies() + 1);
            carts.put(username, cart);
            save();
            return cart;
        }
    }

    @Override
    public Cart checkout(String username) throws IOException {
        synchronized(carts) {
            Cart cart = carts.get(username);
            cart.checkout();
            carts.put(username, cart);
            save();
            return cart;
        }
    }

    @Override
    public Cart applyCoupon(Coupon coupon, String username) throws IOException{
        synchronized(carts){
            Cart cart = carts.get(username);
            cart.applyCoupon(coupon);
            carts.put(username, cart);
            save();
            return cart;
        }
    }
}
