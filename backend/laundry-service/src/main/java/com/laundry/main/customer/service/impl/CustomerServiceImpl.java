package com.laundry.main.customer.service.impl;

import com.laundry.main.customer.entity.Customer;
import com.laundry.main.customer.dto.CustomerRequest;
import com.laundry.main.customer.dto.CustomerResponse;
import com.laundry.main.customer.mapper.CustomerMapper;
import com.laundry.main.customer.service.CustomerService;
import com.laundry.main.exception.DuplicateResourceException;
import com.laundry.main.exception.ResourceNotFoundException;
import com.laundry.main.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        validateUniqueCustomer(request, null);
        Customer customer = customerMapper.toEntity(request);
        if (customer.getActive() == null) {
            customer.setActive(true);
        }
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long customerId) {
        return customerMapper.toResponse(findCustomer(customerId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Override
    public CustomerResponse updateCustomer(Long customerId, CustomerRequest request) {
        Customer customer = findCustomer(customerId);
        validateUniqueCustomer(request, customerId);
        customerMapper.updateEntity(request, customer);
        if (customer.getActive() == null) {
            customer.setActive(true);
        }
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = findCustomer(customerId);
        customerRepository.delete(customer);
    }

    private Customer findCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
    }

    private void validateUniqueCustomer(CustomerRequest request, Long customerId) {
        boolean mobileExists = customerId == null
                ? customerRepository.existsByMobile(request.getMobile())
                : customerRepository.existsByMobileAndCustomerIdNot(request.getMobile(), customerId);
        if (mobileExists) {
            throw new DuplicateResourceException("Customer already exists with mobile: " + request.getMobile());
        }

        boolean emailExists = customerId == null
                ? customerRepository.existsByEmail(request.getEmail())
                : customerRepository.existsByEmailAndCustomerIdNot(request.getEmail(), customerId);
        if (emailExists) {
            throw new DuplicateResourceException("Customer already exists with email: " + request.getEmail());
        }
    }
}
