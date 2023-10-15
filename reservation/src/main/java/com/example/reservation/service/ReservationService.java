package com.example.reservation.service;

import com.example.reservation.repositories.ReservationEntity;
import com.example.reservation.dto.ReservationFeedbackPayload;
import com.example.reservation.repositories.ReservationJPARepository;
import com.example.reservation.dto.ReservationStatus;
import com.example.reservation.dto.BookingPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReservationService {

    @Autowired
    private ReservationJPARepository reservationJPARepository;

    @Value("${app.reservation-feedback-publish}")
    private String reservationFeedbackPublishUrl;

    public void reserve(BookingPayload bookingPayload) {

        try {
            ReservationEntity savedPayment = saveReservation(bookingPayload);
            publishReservationFeedback(bookingPayload, ReservationStatus.CONFIRMED);
        } catch (Exception e) {
            log.error("Payment failed for booking id:{}", bookingPayload.getBookingId());
            publishReservationFeedback(bookingPayload, ReservationStatus.FAILED);
        }
    }

    private void publishReservationFeedback(BookingPayload bookingPayload, ReservationStatus status) {
        WebClient webClient = WebClient.builder()
                .baseUrl(reservationFeedbackPublishUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        var reservationFeedback = new ReservationFeedbackPayload(bookingPayload.getBookingId(), status.name());

        log.info("Publishing Reservation feedback:{}", reservationFeedback);
        webClient.post()
                .body(Mono.just(reservationFeedback), ReservationFeedbackPayload.class)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private ReservationEntity saveReservation(BookingPayload bookingPayload) {
        if (bookingPayload.getNoOfPersons() == 1 || bookingPayload.getNoOfPersons() > 12)
            throw new RuntimeException("Dummy Exception");

        var reservationEntity = ReservationEntity.builder()
                .bookingId(bookingPayload.getBookingId())
                .startDate(bookingPayload.getStartDate())
                .endDate(bookingPayload.getEndDate())
                .status(ReservationStatus.CONFIRMED.name())
                .isFeedBackSent(Boolean.FALSE)
                .build();

        return reservationJPARepository.save(reservationEntity);

    }
}
