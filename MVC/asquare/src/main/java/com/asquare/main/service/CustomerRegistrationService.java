package com.asquare.main.service;

import com.asquare.main.dto.CustomerRegistrationRequest;
import java.util.Optional;

public interface CustomerRegistrationService {

    Optional<CustomerRegistrationRequest> findByMobile(String mobile);

    CustomerRegistrationRequest createCustomer(CustomerRegistrationRequest customer);
}
