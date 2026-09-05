package com.asquare.main.service;

import com.asquare.main.dto.PickupBookingRequest;
import com.asquare.main.dto.PickupBookingResponse;

public interface PickupBookingService {

    PickupBookingResponse bookPickup(Long customerId, PickupBookingRequest request);
}
