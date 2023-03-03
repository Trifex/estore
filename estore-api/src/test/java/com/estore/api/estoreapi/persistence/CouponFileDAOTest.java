package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.estore.api.estoreapi.model.Coupon;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class CouponFileDAOTest {
    CouponFileDAO couponFileDAO;
    ObjectMapper mockObjectMapper;

    private ArrayList<String> testProductNames = new ArrayList<>(List.of(
        "Bag of Fertilizer",
        "Bag of Mulch",
        "Bag of Soil",
        "Bag of Grass Seed"
    ));

    private Coupon[] testCoupons = {
        new Coupon(1, "test1", 10, testProductNames),
        new Coupon(2, "test2", 20, testProductNames),
        new Coupon(3, "test3", 30, testProductNames)
    };

    @BeforeEach
    public void setupCouponFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the coupon array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Coupon[].class))
                .thenReturn(testCoupons);
        couponFileDAO = new CouponFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testUpdateCoupon() throws IOException {
        // Setup
        Coupon coupon = new Coupon(1,"test1", 20, testProductNames);

        // Invoke
        Coupon result = couponFileDAO.updateCoupon(coupon);

        // Analyze
        assertNotNull(result);
    }

    @Test
    public void testUpdateCouponNotFound() throws IOException {
        // Setup
        Coupon coupon = new Coupon(4,"test4", 20, testProductNames);

        // Invoke
        Coupon result = couponFileDAO.updateCoupon(coupon);

        // Analyze
        assertNull(result);
    }

    @Test
    public void testDeleteCoupon() throws IOException {
        // Invoke
        boolean result = couponFileDAO.deleteCoupon("test1");

        // Analyze
        assertTrue(result);
    }

    @Test
    public void testDeleteCouponNotFound() throws IOException {
        // Invoke
        boolean result = couponFileDAO.deleteCoupon("test4");

        // Analyze
        assertFalse(result);
    }
}
