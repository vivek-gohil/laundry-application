package com.asquare.main.controller;

import com.asquare.main.dto.PickupAvailabilityResponse;
import com.asquare.main.service.PickupAvailabilityService;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pickup-slots")
@Slf4j
public class PickupAvailabilityController {

    private final PickupAvailabilityService availabilityService;

    public PickupAvailabilityController(PickupAvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public PickupAvailabilityResponse getAvailability(@RequestParam LocalDate date) {
        log.info("Checking pickup availability for date {}", date);
        if (date.isBefore(LocalDate.now())) {
            log.warn("Rejecting pickup availability request for past date {}", date);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pickup date cannot be in the past.");
        }
        return availabilityService.getAvailability(date);
    }
}
