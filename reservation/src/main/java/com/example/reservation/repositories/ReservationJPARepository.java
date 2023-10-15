package com.example.reservation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJPARepository extends JpaRepository<ReservationEntity, Integer> {
}
