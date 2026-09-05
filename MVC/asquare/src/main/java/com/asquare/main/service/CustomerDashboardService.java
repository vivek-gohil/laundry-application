package com.asquare.main.service;

import com.asquare.main.dto.CustomerBookingSummary;
import java.util.List;

public interface CustomerDashboardService {

    List<CustomerBookingSummary> getCurrentBookings(Long customerId);

    List<CustomerBookingSummary> getPastBookings(Long customerId);
}
