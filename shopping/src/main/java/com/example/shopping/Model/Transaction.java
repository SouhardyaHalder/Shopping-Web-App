package com.example.shopping.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="transaction")
public class Transaction {
    @Id
    public String transactionId;

    @Column(name="userid")
    public int userId;
    @Column(name="orderid")
    public int orderId;
    public String status;
    public int amount;
    public Transaction(String transactionId, int userId, int orderId, String status,int amount) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.orderId = orderId;
        this.status = status;
        this.amount=amount;
    }

    public Transaction() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
