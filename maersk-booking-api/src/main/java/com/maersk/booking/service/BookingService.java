package com.maersk.booking.service;

import com.maersk.booking.api.dto.CheckAvailabilityRequest;
import com.maersk.booking.model.Booking;
import com.maersk.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final WebClient webClient;
    private final BookingRepository bookingRepository;
    private final SequenceGeneratorService sequenceGenerator;

    // A private record for deserializing the external response
    private record MaerskAvailabilityResponse(Integer availableSpace) {}

    public Mono<Boolean> checkAvailability(CheckAvailabilityRequest request) {
        // In a real scenario, the request body would be sent to the external service.
        // For this task, we just call the endpoint.
        return webClient.post()
                .uri("/checkAvailable")
                 .bodyValue(request) // This would send the request data
                .retrieve()
                .bodyToMono(MaerskAvailabilityResponse.class)
                .map(response -> response.availableSpace() != null && response.availableSpace() > 0)
                .doOnError(e -> log.error("Error calling Maersk availability API", e))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)).jitter(0.5))
                .onErrorReturn(false); // If all retries fail, return false
    }

    // In BookingService.java

    public Mono<String> saveBooking(Booking booking) {
        return sequenceGenerator.generateBookingRef()
                .flatMap(bookingRef -> {
                    // Create a new record with the generated bookingRef
                    Booking bookingToSave = new Booking(
                            null, // Mongo generates the internal ID
                            bookingRef,
                            booking.containerSize(),
                            booking.containerType(),
                            booking.origin(),
                            booking.destination(),
                            booking.quantity(),
                            booking.timestamp()
                    );
                    return bookingRepository.save(bookingToSave);
                })
                .map(Booking::bookingRef)
                .doOnSuccess(ref -> log.info("Successfully saved booking with ref: {}", ref))
                .doOnError(e -> log.error("Failed to save booking", e));
    }

}
