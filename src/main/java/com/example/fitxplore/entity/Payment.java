package com.example.fitxplore.entity;

import javax.persistence.*;

// BhxuVyDAESlfMfE4FIx5ZKDi
// rzp_test_KJlRFeNbThTgLB
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long myOrderId;

    private String orderId;

    private String amount;

    private String status;

    private String paymentId;

    private String recepit;

    public Long getMyOrderId() {
        return myOrderId;
    }

    public void setMyOrderId(Long myOrderId) {
        this.myOrderId = myOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getRecepit() {
        return recepit;
    }

    public void setRecepit(String recepit) {
        this.recepit = recepit;
    }
}