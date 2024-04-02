package com.example.shopping.Model;


import jakarta.persistence.*;

@Entity
@Table(name="coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String coupon;

    public Coupon( String coupon) {
        this.coupon = coupon;
    }

    public Coupon() {

    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}
