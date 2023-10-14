package com.example.booking.repositories;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_booking")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String destination;

    private String status;

    private LocalDate startDate;

    private LocalDate endDate;

    private int noOfGuest;

    private long amount;

}
