package com.example.payment.utility;

import com.example.payment.dto.BookingPayload;
import com.example.payment.dto.ReservationFeedbackPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JsonUtility {

    public static String toJsonString(BookingPayload payload) {
        ObjectMapper mapper = creteMapper();
        try {
            return mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Converting object to jason");
        }
    }

    public static BookingPayload toPayment(String payload) {
        ObjectMapper mapper = creteMapper();
        try {
            return mapper.readValue(payload, BookingPayload.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Reading object to jason");
        }
    }

    public static ReservationFeedbackPayload toReservationRequestFeedback(String payload) {
        ObjectMapper mapper = creteMapper();
        try {
            return mapper.readValue(payload, ReservationFeedbackPayload.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Reading object to jason");
        }
    }

    private static ObjectMapper creteMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
