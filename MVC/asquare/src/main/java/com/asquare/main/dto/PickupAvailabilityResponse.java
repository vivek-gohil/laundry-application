package com.asquare.main.dto;

import java.time.LocalDate;
import java.util.List;

public record PickupAvailabilityResponse(
        LocalDate date,
        boolean workingDay,
        String message,
        List<PickupSlotAvailability> slots) {

    public record PickupSlotAvailability(
            Long slotMasterId,
            String startTime,
            String endTime,
            int capacity,
            long bookedCount,
            int remainingCapacity,
            boolean available) {
    }
}
