package com.example.shopping.Model;

import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name="ordered_data")
public class OrderData {
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int orderid;
    private int userid;
    private int quantity;
    private int amount;
    private String coupon;
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    public OrderData( int orderid, int userid, int quantity, int amount, String coupon) {
        this.orderid = orderid;
        this.userid = userid;
        this.quantity = quantity;
        this.amount = amount;
        this.coupon = coupon;
        this.orderDate = new Date();
    }

    public OrderData() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
