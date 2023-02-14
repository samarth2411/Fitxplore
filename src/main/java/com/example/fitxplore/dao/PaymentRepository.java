package com.example.fitxplore.dao;

import com.example.fitxplore.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    public Payment findByOrderId(String orderId);
}