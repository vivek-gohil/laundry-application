package com.asquare.main.controller;

import com.asquare.main.dto.PickupBookingRequest;
import com.asquare.main.dto.PickupBookingResponse;
import com.asquare.main.service.PickupBookingService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pickup-bookings")
@Slf4j
public class PickupBookingController {

    private final PickupBookingService pickupBookingService;

    public PickupBookingController(PickupBookingService pickupBookingService) {
        this.pickupBookingService = pickupBookingService;
    }

    @PostMapping
    public ResponseEntity<PickupBookingResponse> bookPickup(@RequestBody PickupBookingRequest request,
            HttpSession session) {
        Object customerId = session.getAttribute("customerId");
        if (!(customerId instanceof Long id)) {
            log.warn("Blocked unauthenticated pickup booking request");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new PickupBookingResponse(false, "Please log in before booking a pickup.", null));
        }

        PickupBookingResponse response = pickupBookingService.bookPickup(id, request);
        return response.success() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }
}
