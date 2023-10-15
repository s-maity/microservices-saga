package com.example.booking.service;

import com.example.booking.dto.BookingDto;
import com.example.booking.dto.BookingPayload;
import com.example.booking.repositories.BookingEntity;
import com.example.booking.repositories.BookingJPARepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private BookingJPARepository bookingJPARepository;

    @Value("${app.event.publish.booking}")
    private String requestPaymentPublishUrl;

    @Transactional
    public void booking(BookingDto bookingDto) {
        validateRequest(bookingDto);
        BookingEntity savedBooking = saveBooking(bookingDto);
        publishEventForPayment(savedBooking);
    }

    private void validateRequest(BookingDto bookingDto) {
        if (bookingDto.getStartDate()
                .isAfter(bookingDto.getStartDate())) {
            throw new RuntimeException("Startdate cannot be after EndDate");
        }
    }

    private void publishEventForPayment(BookingEntity savedBooking) {
        var bookingPayload = BookingPayload.builder()
                .bookingId(savedBooking.getId())
                .noOfPersons(savedBooking.getNoOfGuest())
                .startDate(savedBooking.getStartDate())
                .endDate(savedBooking.getStartDate())
                .amount(savedBooking.getAmount())
                .build();
        WebClient webClient1 = WebClient.create(requestPaymentPublishUrl);
        log.info("Publishing payment request:{}", bookingPayload);
        webClient1.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(bookingPayload), BookingPayload.class)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private BookingEntity saveBooking(BookingDto bookingDto) {
        var bookingEntity = BookingEntity.builder()
                .name(bookingDto.getGuestName())
                .destination(bookingDto.getDestination())
                .startDate(bookingDto.getStartDate())
                .endDate(bookingDto.getEndDate())
                .status("NA")
                .noOfGuest(bookingDto.getNoOfGuests())
                .amount(calculateAmount(bookingDto))
                .build();
        return bookingJPARepository.save(bookingEntity);
    }

    private long calculateAmount(BookingDto bookingDto) {
        long noOfDays = DAYS.between(bookingDto.getStartDate(), bookingDto.getEndDate());
        return noOfDays * bookingDto.getNoOfGuests() * 1000;
    }

}
