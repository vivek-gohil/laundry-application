package com.asquare.main.service.impl;

import com.asquare.main.entity.Customer;
import com.asquare.main.repository.CustomerRepository;
import com.asquare.main.service.CustomerAuthenticationService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerAuthenticationServiceImpl implements CustomerAuthenticationService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerAuthenticationServiceImpl(CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Customer> login(String mobile, String loginPin) {
        Optional<Customer> customer = findActiveCustomer(mobile);
        if (customer.isEmpty() || loginPin == null || !loginPin.matches("\\d{4}")) {
            return Optional.empty();
        }
        String loginPinHash = customer.get().getLoginPinHash();
        return loginPinHash != null && passwordEncoder.matches(loginPin, loginPinHash) ? customer : Optional.empty();
    }

    private Optional<Customer> findActiveCustomer(String mobile) {
        if (mobile == null || !mobile.matches("\\d{10}")) {
            log.warn("Customer lookup rejected: invalid mobile format");
            return Optional.empty();
        }
        Optional<Customer> customer = customerRepository.findByMobileAndActiveTrue("+91" + mobile);
        if (customer.isPresent()) {
            log.debug("Active customer found using +91 mobile format for mobile={}", maskMobile(mobile));
            return customer;
        }

        customer = customerRepository.findByMobileAndActiveTrue(mobile);
        log.debug("Customer lookup using 10-digit format for mobile={} found={}", maskMobile(mobile), customer.isPresent());
        return customer;
    }

    private String maskMobile(String mobile) {
        if (mobile == null || mobile.length() < 4) {
            return "invalid";
        }
        return "******" + mobile.substring(mobile.length() - 4);
    }
}
