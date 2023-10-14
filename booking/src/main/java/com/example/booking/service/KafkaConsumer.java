package com.example.booking.service;


import com.example.booking.repositories.BookingJPARepository;
import com.example.booking.utility.JsonUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    private final BookingJPARepository bookingJPARepository;

    @KafkaListener(topics = "${app.event.consumer.payment-feedback.topic-name}", groupId = "${app.event.consumer.payment-feedback.consumer-group-id}")
    public void consumePaymentFeedback(String payload) {
        log.info("Payload consumed:{}", payload);
        var paymentFeedback = JsonUtility.toPaymentFeedback(payload);

        var booking = bookingJPARepository.findById(paymentFeedback.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found" + paymentFeedback.getBookingId()));

        booking.setStatus(paymentFeedback.getStatus());
        bookingJPARepository.save(booking);
    }
}
