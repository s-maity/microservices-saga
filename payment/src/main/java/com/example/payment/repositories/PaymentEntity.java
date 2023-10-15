package com.example.payment.repositories;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_payment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int bookingId;

    private String status;

    private long amount;

    private LocalDateTime date;

    private boolean isFeedBackSent;

}
