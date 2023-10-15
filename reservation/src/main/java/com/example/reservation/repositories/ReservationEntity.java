package com.example.reservation.repositories;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_reservation")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int bookingId;

    private String status;

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean isFeedBackSent;

}
