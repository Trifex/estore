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

import com.estore.api.estoreapi.model.Coupon;
import com.estore.api.estoreapi.persistence.CouponDAO;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("coupons")
public class CouponController {
    private static final Logger LOG = Logger.getLogger(CouponController.class.getName());
    private CouponDAO couponDAO;

    public CouponController(CouponDAO couponDAO){
        this.couponDAO = couponDAO;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable String name) {
        LOG.info("GET /coupons/" + name);
        try {
            Coupon coupon = couponDAO.getCoupon(name);
            if (coupon != null)
                return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Coupon[]> getAllCoupons() {
        LOG.info("GET /coupons");
        try {
            Coupon[] coupons = couponDAO.getAllCoupons();
            return new ResponseEntity<Coupon[]>(coupons, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon){
        LOG.info("POST /coupons " + coupon);
        try {
            // Attempt to create coupon
            Coupon createdCoupon = couponDAO.createCoupon(coupon);
            if(createdCoupon == null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            String couponName = createdCoupon.getName();

            // Check to see if coupon exists in persistent storage
            Coupon[] returnedCoupons = getAllCoupons().getBody();
            if(returnedCoupons == null) { // If there are no coupons,then the coupon has not been added properly
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            for(int i = 0; i < returnedCoupons.length; i++) {
                if(returnedCoupons[i].getName().equals(couponName)) {
                    return new ResponseEntity<Coupon>(createdCoupon, HttpStatus.CREATED);
                }
            }

            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon) {
        LOG.info("PUT /coupons " + coupon);
        
        try {
            Coupon found = couponDAO.updateCoupon(coupon);
            if(found != null) {     
                return new ResponseEntity<Coupon>(found, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Coupon> deleteCoupon(@PathVariable String name) {
        LOG.info("DELETE /coupons/" + name);

        try {
            boolean found = couponDAO.deleteCoupon(name);

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