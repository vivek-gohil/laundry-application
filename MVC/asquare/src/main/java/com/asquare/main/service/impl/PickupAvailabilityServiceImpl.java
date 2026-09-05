package com.asquare.main.service.impl;

import com.asquare.main.dto.PickupAvailabilityResponse;
import com.asquare.main.entity.PickupSchedule;
import com.asquare.main.entity.PickupScheduleOverride;
import com.asquare.main.entity.PickupSlotMaster;
import com.asquare.main.repository.PickupBookingRepository;
import com.asquare.main.repository.PickupScheduleOverrideRepository;
import com.asquare.main.repository.PickupScheduleRepository;
import com.asquare.main.repository.PickupSlotMasterRepository;
import com.asquare.main.service.PickupAvailabilityService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PickupAvailabilityServiceImpl implements PickupAvailabilityService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final PickupSlotMasterRepository slotRepository;
    private final PickupScheduleRepository scheduleRepository;
    private final PickupScheduleOverrideRepository exceptionRepository;
    private final PickupBookingRepository bookingRepository;

    public PickupAvailabilityServiceImpl(PickupSlotMasterRepository slotRepository,
            PickupScheduleRepository scheduleRepository,
            PickupScheduleOverrideRepository exceptionRepository,
            PickupBookingRepository bookingRepository) {
        this.slotRepository = slotRepository;
        this.scheduleRepository = scheduleRepository;
        this.exceptionRepository = exceptionRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PickupAvailabilityResponse getAvailability(LocalDate date) {
        boolean workingDay = isWorkingDay(date);
        log.debug("Pickup date {} working day: {}", date, workingDay);
        if (!workingDay) {
            log.info("Pickup unavailable because {} is not a working day", date);
            return new PickupAvailabilityResponse(date, false,
                    "Pickup is unavailable on this date.", List.of());
        }

        List<PickupAvailabilityResponse.PickupSlotAvailability> slots = slotRepository
                .findByActiveTrueOrderByStartTimeAsc()
                .stream()
                .map(slot -> toAvailability(slot, date))
                .toList();
        @SuppressWarnings("null")
        boolean hasAvailableSlot = slots.stream()
                .anyMatch(PickupAvailabilityResponse.PickupSlotAvailability::available);
        log.info("Calculated {} pickup slots for {}; available slot: {}", slots.size(), date, hasAvailableSlot);
        String message = hasAvailableSlot
                ? "Choose an available pickup time."
                : "All pickup times are fully booked for this date.";
        return new PickupAvailabilityResponse(date, true, message, slots);
    }

    private boolean isWorkingDay(LocalDate date) {
        PickupScheduleOverride exception = exceptionRepository
                .findByExceptionDateAndActiveTrue(date)
                .orElse(null);
        if (exception != null) {
            log.debug("Using pickup schedule exception for {}: working day {}", date, exception.isWorkingDay());
            return exception.isWorkingDay();
        }
        @SuppressWarnings("null")
        boolean workingDay = scheduleRepository.findByDayOfWeekAndActiveTrue(date.getDayOfWeek().getValue())
                .map(PickupSchedule::isWorkingDay)
                .orElse(false);
        log.debug("Using weekly pickup schedule for {}: working day {}", date, workingDay);
        return workingDay;
    }

    private PickupAvailabilityResponse.PickupSlotAvailability toAvailability(PickupSlotMaster slot,
            LocalDate date) {
        long bookedCount = bookingRepository.countConfirmedBookings(slot.getSlotMasterId(), date);
        int remainingCapacity = Math.max(0, slot.getCapacity() - Math.toIntExact(bookedCount));
        log.debug("Slot {} on {} has {} bookings and {} remaining capacity",
                slot.getSlotMasterId(), date, bookedCount, remainingCapacity);
        return new PickupAvailabilityResponse.PickupSlotAvailability(
                slot.getSlotMasterId(),
                slot.getStartTime().format(TIME_FORMATTER),
                slot.getEndTime().format(TIME_FORMATTER),
                slot.getCapacity(),
                bookedCount,
                remainingCapacity,
                remainingCapacity > 0);
    }
}
