package com.maersk.booking.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for returning the booking reference number after a successful booking creation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRefResponse {
    private String bookingRef;
}