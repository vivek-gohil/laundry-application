package com.asquare.main.repository;

import com.asquare.main.entity.PickupBooking;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PickupBookingRepository extends JpaRepository<PickupBooking, Long> {

        @Query("select count(b) from PickupBooking b where b.slotMasterId = :slotMasterId "+ "and b.pickupDate = :pickupDate and b.bookingStatus = 'CONFIRMED'")
        long countConfirmedBookings(@Param("slotMasterId") Long slotMasterId, @Param("pickupDate") LocalDate pickupDate);

        boolean existsByCustomerIdAndSlotMasterIdAndPickupDate(Long customerId, Long slotMasterId, LocalDate pickupDate);

        List<PickupBooking> findByCustomerIdOrderByPickupDateDesc(Long customerId);
}
