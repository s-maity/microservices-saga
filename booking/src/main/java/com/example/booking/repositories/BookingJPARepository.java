package com.example.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingJPARepository extends JpaRepository<BookingEntity, Integer> {
}
