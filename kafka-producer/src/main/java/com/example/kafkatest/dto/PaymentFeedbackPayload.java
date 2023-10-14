package com.example.kafkatest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PaymentFeedbackPayload {
    private int bookingId;
    private String status;
}
