package com.example.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReservationFeedbackPayload {
    private int bookingId;
    private String status;
}
