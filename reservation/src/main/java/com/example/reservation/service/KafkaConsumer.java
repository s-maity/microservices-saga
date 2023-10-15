package com.example.reservation.service;

import com.example.reservation.utility.JsonUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ReservationService reservationService;

    @KafkaListener(topics = "${app.topic-name}", groupId = "${app.group-id}")
    public void consume(String bookingPayload) {
        log.info("Consumed payload:{}", bookingPayload);
        var booking = JsonUtility.toBookingPayload(bookingPayload);
        reservationService.reserve(booking);
    }
}
