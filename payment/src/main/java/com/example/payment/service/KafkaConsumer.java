package com.example.payment.service;

import com.example.payment.utility.JsonUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = "${app.topic-name}", groupId = "${app.group-id}")
    public void consume(String bookingPayload) {
        log.info("Consumed payload:{}", bookingPayload);
        var booking = JsonUtility.toPayment(bookingPayload);
        paymentService.payment(booking);
    }
}
