package com.example.payment.utility;

import com.example.payment.dto.BookingPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtility {

    public static String toJsonString(BookingPayload payload) {

        ObjectMapper Obj = new ObjectMapper();

        try {
            return Obj.writeValueAsString(payload);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Converting object to jason");
        }
    }

    public static BookingPayload toPayment(String payload) {

        ObjectMapper Obj = new ObjectMapper();

        try {
            return Obj.readValue(payload, BookingPayload.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error in Reading object to jason");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
