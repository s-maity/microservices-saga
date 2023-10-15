package com.example.reservation.utility;

import com.example.reservation.dto.BookingPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JsonUtility {

    public static String toJsonString(BookingPayload payload) {

        ObjectMapper Obj = creteMapper();
        try {
            return Obj.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Converting object to jason");
        }
    }

    public static BookingPayload toBookingPayload(String payload) {
        ObjectMapper mapper = creteMapper();
        try {
            return mapper.readValue(payload, BookingPayload.class);
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
