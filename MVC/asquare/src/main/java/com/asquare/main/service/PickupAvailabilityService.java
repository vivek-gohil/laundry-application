package com.asquare.main.service;

import com.asquare.main.dto.PickupAvailabilityResponse;
import java.time.LocalDate;

public interface PickupAvailabilityService {

    PickupAvailabilityResponse getAvailability(LocalDate date);
}
