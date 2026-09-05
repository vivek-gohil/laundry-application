package com.asquare.main.dto;

import java.time.LocalDate;

public record CustomerBookingSummary(Long bookingId, LocalDate pickupDate, String timeRange, String status) {
}
