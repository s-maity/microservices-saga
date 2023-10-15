package com.example.kafkatest.service;

import com.example.kafkatest.dto.ReservationFeedbackPayload;
import com.example.kafkatest.dto.ReservationRequestPayload;
import com.example.kafkatest.utility.JsonUtility;
import com.example.kafkatest.dto.BookingPayload;
import com.example.kafkatest.dto.PaymentFeedbackPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaMessagePublisher {

    @Value("${app.topic-name}")
    public String topicName;

    @Value("${app.payment-feedback-topic-name}")
    public String paymentFeedbackTopic;

    @Value("${app.reservation-request-topic-name}")
    public String reservationRequestTopic;

    @Value("${app.reservation-feedback-topic-name}")
    public String reservationFeedbackTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void booking(BookingPayload payload) {
        var jsonPayload = JsonUtility.toJsonString(payload);
        log.info("Publishing booking payload:" + jsonPayload);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate
                .send(topicName, String.valueOf(payload.getBookingId()), jsonPayload);
        handleFuture(future);
    }

    public void paymentFeedback(PaymentFeedbackPayload payload) {
        var jsonPayload = JsonUtility.toJsonString(payload);
        log.info("Publishing payment feedback payload:" + payload);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate
                .send(paymentFeedbackTopic, String.valueOf(payload.getBookingId()), jsonPayload);
        handleFuture(future);
    }

    public void reservationFeedback(ReservationFeedbackPayload payload) {
        var jsonPayload = JsonUtility.toJsonString(payload);
        log.info("Publishing reservation request payload:" + payload);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate
                .send(reservationFeedbackTopic, String.valueOf(payload.getBookingId()), jsonPayload);
        handleFuture(future);
    }


    public void requestReservation(ReservationRequestPayload payload) {
        var jsonPayload = JsonUtility.toJsonString(payload);
        log.info("Publishing reservation request payload:" + payload);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate
                .send(reservationRequestTopic, String.valueOf(payload.getBookingId()), jsonPayload);
        handleFuture(future);
    }

    private static void handleFuture(CompletableFuture<SendResult<String, String>> future) {
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Published Message with offset=" + result.getRecordMetadata()
                        .offset() + " In partition=" + result.getRecordMetadata()
                        .partition());
            } else {
                log.error("Exception when Booking:" + ex.getMessage());
            }
        });
    }


}
