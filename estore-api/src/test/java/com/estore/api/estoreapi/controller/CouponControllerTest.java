package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Coupon;
import com.estore.api.estoreapi.persistence.CouponDAO;

@Tag("Controller-tier")
public class CouponControllerTest {
    private CouponController couponController;
    private CouponDAO mockCouponDAO;

    private ArrayList<String> testProductNames = new ArrayList<>(List.of(
        "Bag of Fertilizer",
        "Bag of Mulch",
        "Bag of Soil",
        "Bag of Grass Seed"
    ));
       
    
    @BeforeEach
    public void setupCouponController() {
        mockCouponDAO = mock(CouponDAO.class);
        couponController = new CouponController(mockCouponDAO);
    }

    @Test
    public void testGetCoupon() throws IOException {
        Coupon coupon = new Coupon(99, "EMPLOYEE20", 20, testProductNames);

        when(mockCouponDAO.getCoupon(coupon.getName())).thenReturn(coupon);

        ResponseEntity<Coupon> response = couponController.getCoupon(coupon.getName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coupon, response.getBody());
    }

    @Test
    public void testGetCouponNotFound() throws IOException {
        String couponName = "EMPLOYEE20";

        when(mockCouponDAO.getCoupon(couponName)).thenReturn(null);

        ResponseEntity<Coupon> response = couponController.getCoupon(couponName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAllCoupons() throws IOException {
        Coupon[] coupons = {
            new Coupon(1, "EMPLOYEE25", 25, testProductNames),
            new Coupon(2, "EMPLOYEE20", 20, testProductNames)
        };

        when(mockCouponDAO.getAllCoupons()).thenReturn(coupons);

        ResponseEntity<Coupon[]> response = couponController.getAllCoupons();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coupons, response.getBody());
    }

    @Test
    public void testCreateCoupon() throws IOException {
        Coupon coupon = new Coupon(99, "EMPLOYEE20", 20, testProductNames);
        Coupon[] coupons = {
            new Coupon(1, "EMPLOYEE25", 25, testProductNames),
            new Coupon(2, "EMPLOYEE30", 30, testProductNames),
            coupon
        };

        when(mockCouponDAO.getAllCoupons()).thenReturn(coupons);
        when(mockCouponDAO.createCoupon(coupon)).thenReturn(coupon);

        ResponseEntity<Coupon> response = couponController.createCoupon(coupon);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(coupon, response.getBody());

        when(mockCouponDAO.getAllCoupons()).thenReturn(null);
        response = couponController.createCoupon(coupon);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateCouponFailed() throws IOException {
        Coupon coupon = new Coupon(99, "EMPLOYEE20", 20, testProductNames);

        when(mockCouponDAO.createCoupon(coupon)).thenReturn(null);
        
        ResponseEntity<Coupon> response = couponController.createCoupon(coupon);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testUpdateCoupon() throws IOException {
        Coupon coupon = new Coupon(99, "EMPLOYEE20", 20, testProductNames);

        when(mockCouponDAO.updateCoupon(coupon)).thenReturn(coupon);

        ResponseEntity<Coupon> response = couponController.updateCoupon(coupon);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coupon, response.getBody());
    }

    @Test
    public void testUpdateCouponFailed() throws IOException {
        Coupon coupon = new Coupon(99, "EMPLOYEE20", 20, testProductNames);

        when(mockCouponDAO.updateCoupon(coupon)).thenReturn(null);

        ResponseEntity<Coupon> response = couponController.updateCoupon(coupon);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteCoupon() throws IOException {
        String couponName = "EMPLOYEE20";

        when(mockCouponDAO.deleteCoupon(couponName)).thenReturn(true);

        ResponseEntity<Coupon> response = couponController.deleteCoupon(couponName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteCouponFailed() throws IOException {
        String couponName = "EMPLOYEE20";

        when(mockCouponDAO.deleteCoupon(couponName)).thenReturn(false);

        ResponseEntity<Coupon> response = couponController.deleteCoupon(couponName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
