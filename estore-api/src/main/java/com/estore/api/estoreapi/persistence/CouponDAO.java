package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Coupon;

public interface CouponDAO {
    Coupon getCoupon(String name) throws IOException;
    Coupon[] getAllCoupons() throws IOException;
    Coupon createCoupon(Coupon coupon) throws IOException;
    Coupon updateCoupon(Coupon coupon) throws IOException;
    boolean deleteCoupon(String name) throws IOException;
}