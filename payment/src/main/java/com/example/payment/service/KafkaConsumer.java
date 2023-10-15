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
    public void consumePaymentRequest(String bookingPayload) {
        log.info("Consumed  payment request payload:{}", bookingPayload);
        var booking = JsonUtility.toPayment(bookingPayload);
        paymentService.payment(booking);
    }

    @KafkaListener(topics = "${app.reservation-feedback-topic-name}", groupId = "${app.group-id}")
    public void consumeReservationFeedback(String reservationFeedback) {
        log.info("Consumed payload:{}", reservationFeedback);
        var reservationFeedbackPayload = JsonUtility.toReservationRequestFeedback(reservationFeedback);
        paymentService.handleReservationRequestFeedback(reservationFeedbackPayload);
    }
}
