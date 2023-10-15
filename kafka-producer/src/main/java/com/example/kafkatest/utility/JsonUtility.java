package com.example.kafkatest.utility;

import com.example.kafkatest.dto.BookingPayload;
import com.example.kafkatest.dto.PaymentFeedbackPayload;
import com.example.kafkatest.dto.ReservationFeedbackPayload;
import com.example.kafkatest.dto.ReservationRequestPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtility {

    public static String toJsonString(BookingPayload payload) {
        ObjectMapper Obj = creteMapper();

        try {
            return Obj.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Converting object to jason");
        }
    }

    public static String toJsonString(PaymentFeedbackPayload payload) {
        ObjectMapper Obj = creteMapper();
        try {
            return Obj.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Converting object to jason");
        }
    }

    public static String toJsonString(ReservationFeedbackPayload payload) {
        ObjectMapper Obj = creteMapper();
        try {
            return Obj.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Converting object to jason");
        }
    }

    public static String toJsonString(ReservationRequestPayload payload) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Converting object to jason");
        }
    }
    private static ObjectMapper creteMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
