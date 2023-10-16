package com.example.payment.service;

import com.example.payment.dto.*;
import com.example.payment.repositories.PaymentEntity;
import com.example.payment.repositories.PaymentJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentJPARepository paymentJPARepository;

    @Value("${app.payment-feedback-publish}")
    private String paymentFeedbackPublishUrl;

    @Value("${app.reservation-request-publish}")
    private String requestReservationPublishUrl;

    @Transactional
    public void payment(BookingPayload bookingPayload) {

        try {
            PaymentEntity savedPayment = savePayment(bookingPayload);
            publishRequestReservation(bookingPayload);
            publishPaymentFeedback(bookingPayload, PaymentStatus.CONFIRMED);
        } catch (Exception e) {
            log.error("Payment failed for booking id:{}", bookingPayload.getBookingId());
            log.error("Error:{}", e.getMessage());
            publishPaymentFeedback(bookingPayload, PaymentStatus.FAILED);
        }
    }

    private void publishRequestReservation(BookingPayload bookingPayload) {
        WebClient webClient = WebClient.builder()
                .baseUrl(requestReservationPublishUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        var reservationRequestPayload = ReservationRequestPayload.builder()
                .bookingId(bookingPayload.getBookingId())
                .startDate(bookingPayload.getStartDate())
                .endDate(bookingPayload.getEndDate())
                .noOfPersons(bookingPayload.getNoOfPersons())
                .build();

        log.info("Publishing Request  reservation:{}", reservationRequestPayload);
        webClient.post()
                .body(Mono.just(reservationRequestPayload), ReservationRequestPayload.class)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private void publishPaymentFeedback(BookingPayload bookingPayload, PaymentStatus status) {

        PaymentEntity paymentEntity = findPaymentByBookingId(bookingPayload.getBookingId());
        paymentEntity.setStatus(status.name());
        paymentEntity.setFeedBackSent(Boolean.TRUE);
        paymentJPARepository.save(paymentEntity);

        WebClient webClient = WebClient.builder()
                .baseUrl(paymentFeedbackPublishUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        var paymentFeedback = new PaymentFeedbackPayload(bookingPayload.getBookingId(), status.name());

        log.info("Publishing payment feedback:{}", paymentFeedback);
        webClient.post()
                .body(Mono.just(paymentFeedback), PaymentFeedbackPayload.class)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private PaymentEntity savePayment(BookingPayload bookingPayload) {
        if (bookingPayload.getAmount() == 1000) throw new RuntimeException("Dummy exception");

        var paymentEntity = PaymentEntity.builder()
                .bookingId(bookingPayload.getBookingId())
                .amount(bookingPayload.getAmount())
                .date(LocalDateTime.now())
                .isFeedBackSent(Boolean.FALSE)
                .build();

        return paymentJPARepository.save(paymentEntity);

    }

    public void handleReservationRequestFeedback(ReservationFeedbackPayload reservationFeedbackPayload) {
        PaymentEntity paymentEntity = findPaymentByBookingId(reservationFeedbackPayload.getBookingId());
        var booking = BookingPayload.builder()
                .bookingId(reservationFeedbackPayload.getBookingId())
                .amount(paymentEntity.getAmount())
                .build();

        if ("CONFIRMED".equals(reservationFeedbackPayload.getStatus())) {
            publishPaymentFeedback(booking, PaymentStatus.CONFIRMED);
        } else {
            if(PaymentStatus.CONFIRMED.name().equals(paymentEntity.getStatus())){
                publishPaymentFeedback(booking, PaymentStatus.REFUND_INITIATED);
            }else{
                publishPaymentFeedback(booking, PaymentStatus.FAILED);
            }
        }

    }

    private PaymentEntity findPaymentByBookingId(int bookingId) {
        return paymentJPARepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("No payment found for booking id:" + bookingId));
    }
}
