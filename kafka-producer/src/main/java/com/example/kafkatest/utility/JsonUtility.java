package com.example.kafkatest.utility;

import com.example.kafkatest.dto.BookingPayload;
import com.example.kafkatest.dto.PaymentFeedbackPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtility {

    public static String toJsonString(BookingPayload payload) {
        ObjectMapper Obj = new ObjectMapper();
        try {
            return Obj.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Converting object to jason");
        }
    }

    public static String toJsonString(PaymentFeedbackPayload payload) {
        ObjectMapper Obj = new ObjectMapper();
        try {
            return Obj.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Converting object to jason");
        }
    }
}
