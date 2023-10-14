package com.example.booking.service;

import com.example.booking.dto.BookingDto;
import com.example.booking.dto.BookingPayload;
import com.example.booking.repositories.BookingEntity;
import com.example.booking.repositories.BookingJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingJPARepository bookingJPARepository;

    public void booking(BookingDto bookingDto) {

        BookingEntity savedBooking = saveBooking(bookingDto);
        publishEventForPayment(savedBooking);
    }

    private void publishEventForPayment(BookingEntity savedBooking) {
        var bookingPayload = new BookingPayload(savedBooking.getId(), savedBooking.getAmount());
        WebClient webClient1 = WebClient.create("http://localhost:8047/api/v1/event/booking");
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
                .status("NA")
                .noOfGuest(bookingDto.getNoOfGuests())
                .amount(calculateAmount(bookingDto))
                .build();
        return bookingJPARepository.save(bookingEntity);
    }

    private long calculateAmount(BookingDto bookingDto) {
        long noOfDays = DAYS.between(bookingDto.getEndDate(), bookingDto.getStartDate());
        return noOfDays * bookingDto.getNoOfGuests() * 1000;
    }

}
