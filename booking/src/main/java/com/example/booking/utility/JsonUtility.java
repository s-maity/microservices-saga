package com.example.booking.utility;

import com.example.booking.dto.PaymentFeedback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtility {

    public static PaymentFeedback toPaymentFeedback(String payload) {
        ObjectMapper Obj = new ObjectMapper();
        try {
            return Obj.readValue(payload, PaymentFeedback.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Reading object to jason");
        }
    }
}
