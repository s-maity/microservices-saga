package com.example.payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentJPARepository extends JpaRepository<PaymentEntity, Integer> {
    Optional<PaymentEntity> findByBookingId(int bookingId);
}
