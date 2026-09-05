package com.asquare.main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "pickup_bookings")
public class PickupBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "slot_master_id", nullable = false)
    private Long slotMasterId;

    @Column(name = "pickup_date", nullable = false)
    private LocalDate pickupDate;

    @Column(name = "booking_status", nullable = false)
    private String bookingStatus;

    protected PickupBooking() {
    }

    public PickupBooking(Long customerId, Long slotMasterId, LocalDate pickupDate) {
        this.customerId = customerId;
        this.slotMasterId = slotMasterId;
        this.pickupDate = pickupDate;
        this.bookingStatus = "CONFIRMED";
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Long getSlotMasterId() {
        return slotMasterId;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }
}
