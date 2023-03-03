package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Coupon;

public interface CartDAO {
    Cart[] getAllCarts() throws IOException;
    Cart getCart(String username) throws IOException;
    Cart createCart(String username) throws IOException;
    Cart addToCart(String username, Product product) throws IOException;
    Cart removeFromCart(String username, Product product) throws IOException;
    Cart checkout(String username) throws IOException;
    Cart applyCoupon(Coupon coupon, String username) throws IOException;
}
