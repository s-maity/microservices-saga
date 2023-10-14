package com.example.payment.service;

import com.example.payment.repositories.PaymentEntity;
import com.example.payment.dto.PaymentFeedbackPayload;
import com.example.payment.repositories.PaymentJPARepository;
import com.example.payment.dto.PaymentStatus;
import com.example.payment.dto.BookingPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentJPARepository paymentJPARepository;

    @Value("${payment-feedback-publish}")
    private String paymentFeedbackPublishUrl;

    public void payment(BookingPayload bookingPayload) {

        PaymentEntity savedPayment = savePayment(bookingPayload);

        publishPaymentFeedback(bookingPayload);
    }

    private void publishPaymentFeedback(BookingPayload bookingPayload) {
        WebClient webClient = WebClient.builder()
                .baseUrl(paymentFeedbackPublishUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        var paymentFeedback = new PaymentFeedbackPayload(bookingPayload.getBookingId(), PaymentStatus.CONFIRMED.name());

        log.info("Publishing payment feedback:{}", paymentFeedback);
        webClient.post()
                .body(Mono.just(paymentFeedback), PaymentFeedbackPayload.class)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private PaymentEntity savePayment(BookingPayload bookingPayload) {
        var paymentEntity = PaymentEntity.builder()
                .bookingId(bookingPayload.getBookingId())
                .amount(bookingPayload.getAmount())
                .date(LocalDateTime.now())
                .status(PaymentStatus.CONFIRMED.name())
                .build();

        return paymentJPARepository.save(paymentEntity);

    }
}
