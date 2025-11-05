package com.maersk.booking.api.dto;

import com.maersk.booking.model.ContainerType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;

@Value // A Lombok annotation for immutable value objects
@Builder
public class CheckAvailabilityRequest {

    @NotNull(message = "Container size is required")
    @Min(value = 20, message = "Container size must be 20 or 40")
    @Max(value = 40, message = "Container size must be 20 or 40")
    Integer containerSize;

    @NotNull(message = "Container type is required")
    ContainerType containerType;

    @NotBlank(message = "Origin is required")
    @Size(min = 5, max = 20, message = "Origin must be between 5 and 20 characters")
    String origin;

    @NotBlank(message = "Destination is required")
    @Size(min = 5, max = 20, message = "Destination must be between 5 and 20 characters")
    String destination;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 100, message = "Quantity cannot exceed 100")
    Integer quantity;
}
    