package com.example.booking.controller;

import com.example.booking.dto.BookingDto;
import com.example.booking.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public void book(@RequestBody BookingDto bookingDto) {
        bookingService.booking(bookingDto);
    }
}
