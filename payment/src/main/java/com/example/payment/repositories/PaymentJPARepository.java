package com.example.payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJPARepository extends JpaRepository<PaymentEntity, Integer> {
}
