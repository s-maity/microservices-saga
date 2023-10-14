package com.example.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class BookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

	@Bean("bookingServicePublish")
	public WebClient webClient() {
		return WebClient.builder()
				.baseUrl("http://localhost:8047/api/v1/event/booking")
				.build();
	}
}
