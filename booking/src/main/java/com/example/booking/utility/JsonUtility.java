package com.example.booking.utility;

import com.example.booking.dto.PaymentFeedback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtility {

    public static PaymentFeedback toPaymentFeedback(String payload) {
        ObjectMapper Obj = creteMapper();
        try {
            return Obj.readValue(payload, PaymentFeedback.class);
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
