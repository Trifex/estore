package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Coupon;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CouponFileDAO implements CouponDAO {

    Map<String, Coupon> coupons;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    public CouponFileDAO(@Value("${coupons.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    public Map<String, Coupon> getCoupons() {
        return coupons;
    }

    private static synchronized int nextId() {
        return nextId;
    }

    private boolean save() throws IOException {
        Coupon[] couponsArray = getAllCoupons();
        objectMapper.writeValue(new File(filename),couponsArray);
        load();
        return true;
    }

    private boolean load() throws IOException {
        coupons = new TreeMap<>();
        nextId = 0;
        Coupon[] couponArray = objectMapper.readValue(new File(filename),Coupon[].class);

        for (Coupon coupon : couponArray) {
            coupons.put(coupon.getName(), coupon);
        }

        int next = coupons.size() + 1; // The default next product id when we load is the end id + 1

        // Loop through all possible integers and if there is one missing, set that to be the next product added
        for(Coupon coupon : coupons.values()){
            if(!coupons.keySet().contains(coupon.getName())){
                next = 1;
                break;
            }
        }

        nextId = next;

        return true;
    }

    @Override
    public Coupon getCoupon(String name) throws IOException {
        return coupons.get(name); // Returns null if no product found
    }

    @Override
    public Coupon[] getAllCoupons() throws IOException {
        synchronized(coupons) {
            return coupons.values().toArray(new Coupon[0]);
        }
    }

    @Override
    public Coupon createCoupon(Coupon coupon) throws IOException {
        synchronized(coupons){
            Coupon newCoupon = new Coupon(nextId(), coupon.getName(), coupon.getDiscount(), coupon.getProducts());
            coupons.put(newCoupon.getName(), newCoupon);
            save();
            return newCoupon;
        }
    }

    @Override
    public Coupon updateCoupon(Coupon coupon) throws IOException {
        synchronized(coupons){
            Coupon updated = coupons.replace(coupon.getName(), coupon) != null ? coupon : null; // Returns null if no product found
            save();
            return updated;
        }
    }

    @Override
    public boolean deleteCoupon(String name) throws IOException {
        synchronized(coupons){
            Coupon removed = coupons.remove(name);
            if(removed != null)
                nextId = removed.getId();
            save();
            return removed != null;
        }
    }
}