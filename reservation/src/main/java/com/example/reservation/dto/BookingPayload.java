package com.example.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookingPayload {
    private int bookingId;
    private int noOfPersons;
    private LocalDate startDate;
    private LocalDate endDate;
}
