package com.asquare.main.service.impl;

import com.asquare.main.dto.CustomerRegistrationRequest;
import com.asquare.main.entity.Customer;
import com.asquare.main.entity.Wing;
import com.asquare.main.repository.CustomerRepository;
import com.asquare.main.repository.WingRepository;
import com.asquare.main.service.CustomerRegistrationService;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    private final CustomerRepository customerRepository;
    private final WingRepository wingRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerRegistrationServiceImpl(CustomerRepository customerRepository, WingRepository wingRepository,
            PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.wingRepository = wingRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<CustomerRegistrationRequest> findByMobile(String mobile) {
        return customerRepository.findByMobileAndActiveTrue(normalizeMobile(mobile))
                .map(customer -> new CustomerRegistrationRequest(
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getFlatNumber(),
                        customer.getEmail(),
                        customer.getMobile(),
                        null,
                        null,
                        null,
                        null,
                        customer.getWingId()));
    }

    @Override
    @Transactional
    public CustomerRegistrationRequest createCustomer(CustomerRegistrationRequest customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer registration details are required.");
        }

        String normalizedFirstName = normalizeName(customer.firstName());
        String normalizedLastName = normalizeName(customer.lastName());
        String normalizedFlatNumber = normalizeFlatNumber(customer.flatNumber());
        String normalizedEmail = normalizeEmail(customer.email());
        String normalizedMobile = normalizeMobile(customer.mobile());
        String loginPin = customer.loginPin();
        Long builderId = customer.builderId();
        Long projectId = customer.projectId();
        Long clusterId = customer.clusterId();
        Long wingId = customer.wingId();

        if (normalizedFirstName.isEmpty() || normalizedFlatNumber.isEmpty() || normalizedMobile.isEmpty()
                || builderId == null || projectId == null || clusterId == null || wingId == null || loginPin == null || loginPin.isBlank()) {
            throw new IllegalArgumentException("Builder, project, cluster, wing, first name, flat number, mobile number, and a login PIN are required.");
        }

        if (!normalizedMobile.matches("^\\+[1-9]\\d{9,14}$")) {
            throw new IllegalArgumentException("Mobile number must include a valid country code and digits.");
        }

        if (!loginPin.matches("^\\d{4}$")) {
            throw new IllegalArgumentException("Login PIN must be exactly 4 digits.");
        }

        if (normalizedEmail != null && !normalizedEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }

        Wing wing = wingRepository.findById(wingId)
                .orElseThrow(() -> new IllegalArgumentException("Selected wing is not available."));

        if (!wing.isActive()) {
            throw new IllegalArgumentException("Selected wing is currently unavailable.");
        }

        if (wing.getCluster() == null || wing.getCluster().getProject() == null || wing.getCluster().getProject().getBuilder() == null) {
            throw new IllegalArgumentException("Selected wing is not mapped to a valid builder, project, and cluster.");
        }

        if (!wing.getCluster().getClusterId().equals(clusterId)
                || !wing.getCluster().getProject().getProjectId().equals(projectId)
                || !wing.getCluster().getProject().getBuilder().getBuilderId().equals(builderId)) {
            throw new IllegalArgumentException("Selected builder, project, cluster, and wing must belong to the same hierarchy.");
        }

        if (customerRepository.existsByMobileAndActiveTrue(normalizedMobile)) {
            throw new IllegalArgumentException("This mobile number is already registered.");
        }

        if (customerRepository.existsByWingIdAndFlatNumberAndActiveTrue(wingId, normalizedFlatNumber)) {
            throw new IllegalArgumentException("This flat number is already registered for the selected wing.");
        }

        Customer entity = new Customer();
        entity.setFirstName(normalizedFirstName);
        entity.setLastName(normalizedLastName);
        entity.setWingId(wingId);
        entity.setFlatNumber(normalizedFlatNumber);
        entity.setEmail(normalizedEmail);
        entity.setMobile(normalizedMobile);
        entity.setLoginPinHash(passwordEncoder.encode(loginPin));
        entity.setActive(true);

        Customer saved = customerRepository.save(entity);
        return new CustomerRegistrationRequest(
                saved.getFirstName(),
                saved.getLastName(),
                saved.getFlatNumber(),
                saved.getEmail(),
                saved.getMobile(),
                null,
                builderId,
                projectId,
                clusterId,
                saved.getWingId());
    }

    private String normalizeName(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    private String normalizeFlatNumber(String value) {
        return normalizeName(value);
    }

    private String normalizeEmail(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private String normalizeMobile(String value) {
        if (value == null) {
            return "";
        }
        String digits = value.replaceAll("\\D", "");
        if (digits.length() == 10) {
            return "+91" + digits;
        }
        if (digits.length() > 10) {
            return "+" + digits;
        }
        return digits;
    }
}
