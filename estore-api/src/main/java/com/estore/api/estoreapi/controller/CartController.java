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
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Coupon;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.CartDAO;
import com.estore.api.estoreapi.persistence.CouponDAO;
import com.estore.api.estoreapi.persistence.InventoryDAO;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("carts")
public class CartController {
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    private CartDAO cartDAO;
    private InventoryDAO inventoryDAO;
    private CouponDAO couponDAO;

    public CartController(CartDAO cartDAO, InventoryDAO inventoryDAO, CouponDAO couponDAO) {
        this.cartDAO = cartDAO;
        this.inventoryDAO = inventoryDAO;
        this.couponDAO = couponDAO;
    }

    @GetMapping("/{username}")
    public ResponseEntity<Cart> getCart(@PathVariable String username) {
        LOG.info("GET /carts/" + username);
        try {
            Cart cart = cartDAO.getCart(username);
            if(cart != null) {
                return new ResponseEntity<>(cart, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Cart[]> getAllCarts() {
        LOG.info("GET /carts/");
        try {
            Cart[] carts = cartDAO.getAllCarts();
            return new ResponseEntity<>(carts, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        LOG.info("POST /carts/create/ " + cart);
        try {
            Cart newCart = cartDAO.createCart(cart.getUsername());
            return new ResponseEntity<>(newCart, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}/{username}/add")
    public ResponseEntity<Cart> addToCart(@PathVariable String username, @PathVariable int id) throws IOException {
        try {
            Product product = inventoryDAO.getProduct(id);
            LOG.info("PUT /carts/" + id + "/" + username + "/add " + product);
            if(product != null){
                Cart newCart = cartDAO.addToCart(username, product);
                return new ResponseEntity<>(newCart, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}/{username}/remove")
    public ResponseEntity<Cart> removeFromCart(@PathVariable String username, @PathVariable int id) throws IOException {
        try {
            Product product = inventoryDAO.getProduct(id);
            LOG.info("PUT /carts/" + id + "/" + username + "/remove " + product);
            if(product != null){
                Cart newCart = cartDAO.removeFromCart(username, product);
                return new ResponseEntity<>(newCart, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Cart> checkout(@PathVariable String username) {
        LOG.info("DELETE /carts/" + username);
        try {
            Cart newCart = cartDAO.checkout(username);
            if(newCart != null){
                return new ResponseEntity<Cart>(newCart, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{username}/apply")
    public ResponseEntity<Cart> applyCoupon(@PathVariable String username, @RequestBody String couponName) throws IOException{
        Coupon coupon = couponDAO.getCoupon(couponName);
        LOG.info("PUT / carts/" + username + "/" + coupon);
        try{
            Cart newCart = cartDAO.applyCoupon(coupon, username);
            if(newCart != null){
                return new ResponseEntity<Cart>(newCart, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
