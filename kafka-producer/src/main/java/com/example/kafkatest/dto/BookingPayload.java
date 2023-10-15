package com.example.kafkatest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingPayload implements Serializable {
    private int bookingId;
    private long amount;
    private int noOfPersons;
    private LocalDate startDate;
    private LocalDate endDate;
}
