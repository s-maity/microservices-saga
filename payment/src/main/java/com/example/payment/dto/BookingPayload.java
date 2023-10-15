package com.example.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
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
