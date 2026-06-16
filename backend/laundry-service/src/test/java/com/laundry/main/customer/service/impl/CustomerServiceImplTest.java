package com.laundry.main.customer.service.impl;

import com.laundry.main.customer.entity.Customer;
import com.laundry.main.customer.dto.CustomerRequest;
import com.laundry.main.customer.dto.CustomerResponse;
import com.laundry.main.customer.mapper.CustomerMapper;
import com.laundry.main.exception.DuplicateResourceException;
import com.laundry.main.exception.ResourceNotFoundException;
import com.laundry.main.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void createCustomerSavesAndReturnsCustomer() {
        CustomerRequest request = customerRequest();
        Customer customer = customer();
        Customer savedCustomer = customer();
        CustomerResponse response = customerResponse();

        when(customerRepository.existsByMobile(request.getMobile())).thenReturn(false);
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(customerMapper.toEntity(request)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(savedCustomer);
        when(customerMapper.toResponse(savedCustomer)).thenReturn(response);

        CustomerResponse result = customerService.createCustomer(request);

        assertThat(result).isEqualTo(response);
        verify(customerRepository).save(customer);
    }

    @Test
    void createCustomerThrowsExceptionWhenMobileAlreadyExists() {
        CustomerRequest request = customerRequest();

        when(customerRepository.existsByMobile(request.getMobile())).thenReturn(true);

        assertThatThrownBy(() -> customerService.createCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Customer already exists with mobile: 9876543210");
    }

    @Test
    void getCustomerByIdReturnsCustomer() {
        Customer customer = customer();
        CustomerResponse response = customerResponse();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.toResponse(customer)).thenReturn(response);

        CustomerResponse result = customerService.getCustomerById(1L);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void getCustomerByIdThrowsExceptionWhenCustomerNotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getCustomerById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer not found with id: 99");
    }

    @Test
    void getAllCustomersReturnsCustomers() {
        Customer customer = customer();
        CustomerResponse response = customerResponse();

        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(customerMapper.toResponse(customer)).thenReturn(response);

        List<CustomerResponse> result = customerService.getAllCustomers();

        assertThat(result).containsExactly(response);
    }

    @Test
    void updateCustomerUpdatesAndReturnsCustomer() {
        CustomerRequest request = customerRequest();
        Customer customer = customer();
        Customer savedCustomer = customer();
        CustomerResponse response = customerResponse();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.existsByMobileAndCustomerIdNot(request.getMobile(), 1L)).thenReturn(false);
        when(customerRepository.existsByEmailAndCustomerIdNot(request.getEmail(), 1L)).thenReturn(false);
        when(customerRepository.save(customer)).thenReturn(savedCustomer);
        when(customerMapper.toResponse(savedCustomer)).thenReturn(response);

        CustomerResponse result = customerService.updateCustomer(1L, request);

        assertThat(result).isEqualTo(response);
        verify(customerMapper).updateEntity(request, customer);
    }

    @Test
    void deleteCustomerDeletesExistingCustomer() {
        Customer customer = customer();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);

        customerService.deleteCustomer(1L);

        verify(customerRepository).delete(customer);
    }

    private CustomerRequest customerRequest() {
        return CustomerRequest.builder()
                .firstName("Rahul")
                .lastName("Sharma")
                .mobile("9876543210")
                .email("rahul.sharma@example.com")
                .flatNumber("A-204")
                .buildingName("Green Heights")
                .addressLine1("12 MG Road")
                .addressLine2("Near Central Mall")
                .city("Bengaluru")
                .pincode("560001")
                .landmark("Opposite Metro Station")
                .active(true)
                .build();
    }

    private Customer customer() {
        return Customer.builder()
                .customerId(1L)
                .firstName("Rahul")
                .lastName("Sharma")
                .mobile("9876543210")
                .email("rahul.sharma@example.com")
                .flatNumber("A-204")
                .buildingName("Green Heights")
                .addressLine1("12 MG Road")
                .addressLine2("Near Central Mall")
                .city("Bengaluru")
                .pincode("560001")
                .landmark("Opposite Metro Station")
                .active(true)
                .build();
    }

    private CustomerResponse customerResponse() {
        return CustomerResponse.builder()
                .customerId(1L)
                .firstName("Rahul")
                .lastName("Sharma")
                .mobile("9876543210")
                .email("rahul.sharma@example.com")
                .flatNumber("A-204")
                .buildingName("Green Heights")
                .addressLine1("12 MG Road")
                .addressLine2("Near Central Mall")
                .city("Bengaluru")
                .pincode("560001")
                .landmark("Opposite Metro Station")
                .active(true)
                .build();
    }
}
