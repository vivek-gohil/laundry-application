package com.asquare.main.service.impl;

import com.asquare.main.dto.PickupAvailabilityResponse;
import com.asquare.main.dto.PickupBookingRequest;
import com.asquare.main.dto.PickupBookingResponse;
import com.asquare.main.entity.PickupBooking;
import com.asquare.main.entity.PickupSlotMaster;
import com.asquare.main.repository.PickupBookingRepository;
import com.asquare.main.repository.PickupSlotMasterRepository;
import com.asquare.main.service.PickupAvailabilityService;
import com.asquare.main.service.PickupBookingService;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PickupBookingServiceImpl implements PickupBookingService {

    private final PickupAvailabilityService availabilityService;
    private final PickupSlotMasterRepository slotRepository;
    private final PickupBookingRepository bookingRepository;

    public PickupBookingServiceImpl(PickupAvailabilityService availabilityService,
            PickupSlotMasterRepository slotRepository, PickupBookingRepository bookingRepository) {
        this.availabilityService = availabilityService;
        this.slotRepository = slotRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public PickupBookingResponse bookPickup(Long customerId, PickupBookingRequest request) {
        if (request == null || request.pickupDate() == null || request.slotMasterId() == null) {
            return new PickupBookingResponse(false, "Pickup date and time slot are required.", null);
        }
        if (request.pickupDate().isBefore(LocalDate.now())) {
            return new PickupBookingResponse(false, "Pickup date cannot be in the past.", null);
        }

        PickupAvailabilityResponse availability = availabilityService.getAvailability(request.pickupDate());
        boolean selectedSlotIsAvailable = availability.slots().stream()
                .anyMatch(slot -> slot.slotMasterId().equals(request.slotMasterId()) && slot.available());
        if (!selectedSlotIsAvailable) {
            return new PickupBookingResponse(false, "This pickup slot is no longer available.", null);
        }

        PickupSlotMaster slot = slotRepository.findBySlotMasterIdAndActiveTrue(request.slotMasterId()).orElse(null);
        if (slot == null) {
            return new PickupBookingResponse(false, "This pickup slot is no longer available.", null);
        }
        if (bookingRepository.existsByCustomerIdAndSlotMasterIdAndPickupDate(customerId, request.slotMasterId(),
                request.pickupDate())) {
            return new PickupBookingResponse(false, "You already have this pickup slot booked.", null);
        }

        long confirmedBookings = bookingRepository.countConfirmedBookings(request.slotMasterId(), request.pickupDate());
        if (confirmedBookings >= slot.getCapacity()) {
            return new PickupBookingResponse(false, "This pickup slot was just fully booked. Please choose another time.", null);
        }

        PickupBooking booking = bookingRepository
                .save(new PickupBooking(customerId, request.slotMasterId(), request.pickupDate()));
        log.info("Pickup booked: bookingId={}, customerId={}, slotId={}, date={}", booking.getBookingId(), customerId,
                request.slotMasterId(), request.pickupDate());
        return new PickupBookingResponse(true, "Your pickup has been booked successfully.", booking.getBookingId());
    }
}
