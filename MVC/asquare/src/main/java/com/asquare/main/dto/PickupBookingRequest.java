package com.asquare.main.dto;

import java.time.LocalDate;

public record PickupBookingRequest(LocalDate pickupDate, Long slotMasterId) {
}
