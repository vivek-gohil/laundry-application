package com.asquare.main.controller;

import com.asquare.main.repository.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CustomerModelAdvice {

    private final CustomerRepository customerRepository;

    public CustomerModelAdvice(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @ModelAttribute("customerName")
    public String customerName(HttpSession session) {
        Object customerId = session.getAttribute("customerId");
        if (!(customerId instanceof Long id)) {
            return null;
        }
        return customerRepository.findById(id).map(customer -> customer.getFirstName()).orElse(null);
    }
}
