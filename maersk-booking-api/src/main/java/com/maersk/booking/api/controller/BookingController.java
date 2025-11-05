package com.maersk.booking.api.controller;

import com.maersk.booking.api.dto.AvailabilityResponse;
import com.maersk.booking.api.dto.BookingRefResponse;
import com.maersk.booking.api.dto.CheckAvailabilityRequest;
import com.maersk.booking.model.Booking;
import com.maersk.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/check-available")
    public Mono<AvailabilityResponse> checkAvailability(@Valid @RequestBody CheckAvailabilityRequest request) {
        return bookingService.checkAvailability(request)
                .map(AvailabilityResponse::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<BookingRefResponse> bookContainer(@Valid @RequestBody Booking booking) {
        return bookingService.saveBooking(booking)
                .map(BookingRefResponse::new);
    }
}
