package com.maersk.booking.api.controller;

import com.maersk.booking.api.dto.CheckAvailabilityRequest;
import com.maersk.booking.model.Booking;
import com.maersk.booking.model.ContainerType;
import com.maersk.booking.repository.BookingRepository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = BookingControllerTest.Initializer.class)
class BookingControllerTest {

    public static MockWebServer mockMaerskApi;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeAll
    static void setUp() throws IOException {
        mockMaerskApi = new MockWebServer();
        mockMaerskApi.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockMaerskApi.shutdown();
    }

    @BeforeEach
    void reset() {
        bookingRepository.deleteAll().block();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "maersk.api.base-url=" + mockMaerskApi.url("/").toString()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    void checkAvailability_whenSpaceIsAvailable_returnsTrue() {
        mockMaerskApi.enqueue(new MockResponse()
                .setBody("{\"availableSpace\": 6}")
                .addHeader("Content-Type", "application/json"));

        CheckAvailabilityRequest request = CheckAvailabilityRequest.builder()
                .containerSize(20).containerType(ContainerType.DRY)
                .origin("Southampton").destination("Singapore")
                .quantity(5).build();

        webTestClient.post().uri("/api/bookings/check-available")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.available").isEqualTo(true);
    }

    @Test
    void checkAvailability_whenSpaceIsNotAvailable_returnsFalse() {
        mockMaerskApi.enqueue(new MockResponse()
                .setBody("{\"availableSpace\": 0}")
                .addHeader("Content-Type", "application/json"));

        CheckAvailabilityRequest request = CheckAvailabilityRequest.builder()
                .containerSize(40).containerType(ContainerType.REEFER)
                .origin("Southampton").destination("Singapore")
                .quantity(5).build();

        webTestClient.post().uri("/api/bookings/check-available")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.available").isEqualTo(false);
    }

    @Test
    void bookContainer_whenRequestIsValid_returnsBookingRef() {
        Booking bookingRequest = new Booking(null, null, 20, ContainerType.DRY, "Southampton", "Singapore", 5, Instant.now());

        webTestClient.post().uri("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookingRequest)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                .jsonPath("$.bookingRef").isEqualTo("957000001");

        // Verify it was saved to the database
        assertThat(bookingRepository.count().block()).isEqualTo(1);
        assertThat(bookingRepository.findAll().blockFirst().bookingRef()).isEqualTo("957000001");
    }

    @Test
    void bookContainer_whenQuantityIsInvalid_returnsBadRequest() {
        Booking bookingRequest = new Booking(null, null, 20, ContainerType.DRY, "Southampton", "Singapore", 101, Instant.now());

        webTestClient.post().uri("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookingRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
