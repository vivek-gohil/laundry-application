package com.laundry.main.customer.service;

import com.laundry.main.customer.dto.CustomerRequest;
import com.laundry.main.customer.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse getCustomerById(Long customerId);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse updateCustomer(Long customerId, CustomerRequest request);

    void deleteCustomer(Long customerId);
}
