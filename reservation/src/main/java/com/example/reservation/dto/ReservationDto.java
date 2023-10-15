package com.example.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
@Getter
@Setter
public class ReservationDto {
    private int bookingId;
    private int noOfPersons;
}
