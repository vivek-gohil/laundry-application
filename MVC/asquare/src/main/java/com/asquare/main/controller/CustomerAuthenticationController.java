package com.asquare.main.controller;

import com.asquare.main.dto.AuthResponse;
import com.asquare.main.dto.PinLoginRequest;
import com.asquare.main.entity.Customer;
import com.asquare.main.service.CustomerAuthenticationService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class CustomerAuthenticationController {

    private final CustomerAuthenticationService customerAuthenticationService;

    public CustomerAuthenticationController(CustomerAuthenticationService customerAuthenticationService) {
        this.customerAuthenticationService = customerAuthenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody PinLoginRequest request, HttpSession session) {
        log.info("PIN login requested for mobile={}", maskMobile(request.mobile()));
        Optional<Customer> customer = customerAuthenticationService.login(request.mobile(), request.loginPin());
        if (customer.isEmpty()) {
            log.warn("PIN login failed for mobile={}", maskMobile(request.mobile()));
            return ResponseEntity.badRequest().body(new AuthResponse(false, "Invalid mobile number or login PIN."));
        }

        session.setAttribute("customerId", customer.get().getCustomerId());
        session.setAttribute("customerMobile", customer.get().getMobile());
        log.info("Customer login succeeded: customerId={}, mobile={}, sessionId={}", customer.get().getCustomerId(),
                maskMobile(customer.get().getMobile()), session.getId());
        return ResponseEntity.ok(new AuthResponse(true, "Logged in successfully."));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpSession session) {
        String sessionId = session.getId();
        Object customerId = session.getAttribute("customerId");
        session.invalidate();
        log.info("Customer logout completed: customerId={}, sessionId={}", customerId, sessionId);
        return ResponseEntity.ok(new AuthResponse(true, "Logged out successfully."));
    }

    private String maskMobile(String mobile) {
        if (mobile == null || mobile.length() < 4) {
            return "invalid";
        }
        return "******" + mobile.substring(mobile.length() - 4);
    }
}
