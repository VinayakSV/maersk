package com.maersk.booking.model;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

// Using a Java 17 Record for an immutable data model
@Document("bookings")
public record Booking(
        @Id
        String id, // Internal MongoDB ID

        @Field("booking_ref")
        String bookingRef,

        @Field("container_size")
        @NotNull @Min(20) @Max(40)
        Integer containerSize,

        @Field("container_type")
        @NotNull
        ContainerType containerType,

        @Field("origin")
        @NotBlank @Size(min = 5, max = 20)
        String origin,

        @Field("destination")
        @NotBlank @Size(min = 5, max = 20)
        String destination,

        @Field("quantity")
        @NotNull @Min(1) @Max(100)
        Integer quantity,

        @Field("timestamp")
        @NotNull
        Instant timestamp
) {}
