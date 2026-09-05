package com.asquare.main.service;

import com.asquare.main.entity.Customer;
import java.util.Optional;

public interface CustomerAuthenticationService {

    Optional<Customer> login(String mobile, String loginPin);
}
