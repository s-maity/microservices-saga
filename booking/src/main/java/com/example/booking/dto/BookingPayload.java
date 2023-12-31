package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookingPayload {
    private int bookingId;
    private long amount;
    private int noOfPersons;
    private LocalDate startDate;
    private LocalDate endDate;
}
