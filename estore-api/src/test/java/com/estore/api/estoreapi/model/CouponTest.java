package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag ("Model-tier")
public class CouponTest {
    private ArrayList<String> testProductNames = new ArrayList<>(List.of(
        "Bag of Fertilizer",
        "Bag of Mulch",
        "Bag of Soil",
        "Bag of Grass Seed"
    ));

    @Test
    public void testAddProduct() throws IOException {
        Coupon coupon = new Coupon(1, "EMPLOYEE20", 20, testProductNames);
        String productName = "Test Product";

        coupon.addProduct(productName);
        ArrayList<String> result = coupon.getProducts();

        boolean contains = result.contains(productName);
        assertTrue(contains);

        // Test duplication
        coupon.addProduct(productName);
        result = coupon.getProducts();  

        int duplicateCount = 0;
        for(String product : result) {
            if(product.equals(productName))
                duplicateCount++;
        }

        assertEquals(1, duplicateCount);
    }
}