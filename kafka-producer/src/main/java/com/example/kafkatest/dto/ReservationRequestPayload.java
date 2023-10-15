package com.example.kafkatest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ReservationRequestPayload {
    private int bookingId;
    private int noOfPersons;
    private LocalDate startDate;
    private LocalDate endDate;
}
