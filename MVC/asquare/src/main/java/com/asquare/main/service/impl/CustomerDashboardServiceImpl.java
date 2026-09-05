package com.asquare.main.service.impl;

import com.asquare.main.dto.CustomerBookingSummary;
import com.asquare.main.entity.PickupBooking;
import com.asquare.main.entity.PickupSlotMaster;
import com.asquare.main.repository.PickupBookingRepository;
import com.asquare.main.repository.PickupSlotMasterRepository;
import com.asquare.main.service.CustomerDashboardService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerDashboardServiceImpl implements CustomerDashboardService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final PickupBookingRepository bookingRepository;
    private final PickupSlotMasterRepository slotRepository;

    public CustomerDashboardServiceImpl(PickupBookingRepository bookingRepository,
            PickupSlotMasterRepository slotRepository) {
        this.bookingRepository = bookingRepository;
        this.slotRepository = slotRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerBookingSummary> getCurrentBookings(Long customerId) {
        return bookingsFor(customerId).stream()
                .filter(booking -> !booking.pickupDate().isBefore(LocalDate.now(ZoneId.systemDefault())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerBookingSummary> getPastBookings(Long customerId) {
        return bookingsFor(customerId).stream()
                .filter(booking -> booking.pickupDate().isBefore(LocalDate.now(ZoneId.systemDefault())))
                .toList();
    }

    private List<CustomerBookingSummary> bookingsFor(Long customerId) {
        List<PickupBooking> bookings = bookingRepository.findByCustomerIdOrderByPickupDateDesc(customerId);
        @SuppressWarnings("null")
        Map<Long, PickupSlotMaster> slots = slotRepository.findAllById(bookings.stream()
                .map(PickupBooking::getSlotMasterId)
                .toList())
                .stream()
                .collect(Collectors.toMap(PickupSlotMaster::getSlotMasterId, Function.identity()));

        return bookings.stream().map(booking -> {
            PickupSlotMaster slot = slots.get(booking.getSlotMasterId());
            String timeRange = slot == null ? "Slot unavailable" : slot.getStartTime().format(TIME_FORMATTER)
                    + " - " + slot.getEndTime().format(TIME_FORMATTER);
            return new CustomerBookingSummary(booking.getBookingId(), booking.getPickupDate(), timeRange,
                    booking.getBookingStatus());
        }).toList();
    }
}
