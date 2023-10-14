package com.example.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Getter
@Setter
public class PaymentDto {
    private int bookingId;
    private long amount;
}
