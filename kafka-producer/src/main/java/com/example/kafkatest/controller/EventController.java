package com.example.kafkatest.controller;

import com.example.kafkatest.dto.ReservationFeedbackPayload;
import com.example.kafkatest.dto.ReservationRequestPayload;
import com.example.kafkatest.service.KafkaMessagePublisher;
import com.example.kafkatest.dto.BookingPayload;
import com.example.kafkatest.dto.PaymentFeedbackPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    @Autowired
    private KafkaMessagePublisher publisher;

    @PostMapping("/booking")
    public ResponseEntity<String> publish(@RequestBody BookingPayload bookingPayload) {
        try {
            publisher.booking(bookingPayload);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/payment-feedback")
    public ResponseEntity<String> paymentFeedBack(@RequestBody PaymentFeedbackPayload payload) {
        try {
            publisher.paymentFeedback(payload);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/reservation-request")
    public ResponseEntity<String> requestReservation(@RequestBody ReservationRequestPayload payload) {
        try {
            publisher.requestReservation(payload);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/reservation-feedback")
    public ResponseEntity<String> reservationFeedBack(@RequestBody ReservationFeedbackPayload payload) {
        try {
            publisher.reservationFeedback(payload);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
