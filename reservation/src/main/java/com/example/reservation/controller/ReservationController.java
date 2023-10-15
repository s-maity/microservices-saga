package com.example.reservation.controller;

import com.example.reservation.service.ReservationService;
import com.example.reservation.dto.ReservationDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservation")
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public void payment(@RequestBody ReservationDto reservationDto) {
    }
}
