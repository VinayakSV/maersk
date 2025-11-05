package com.maersk.booking.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class SequenceGeneratorService {

    // Starts at 957000000 so the first generated ID is 957000001
    private final AtomicLong counter = new AtomicLong(957000000L);

    public Mono<String> generateBookingRef() {
        return Mono.fromSupplier(() -> String.valueOf(counter.incrementAndGet()));
    }
}
