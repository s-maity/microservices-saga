package com.example.payment.repositories;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_payment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int bookingId;

    private String status;

    private long amount;

    private LocalDateTime date;

}
