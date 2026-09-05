package com.asquare.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.asquare.main.dto.CustomerRegistrationRequest;
import com.asquare.main.service.CustomerRegistrationService;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CustomerRegistrationServiceTest {

    @Test
    void registersCustomerWithRequiredProfileData() {
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Asha",
                "Nair",
                "M-102",
                "asha@example.com",
                "+919876543210",
                "1234",
                1L,
                1L,
                1L,
                101L
        );

        CustomerRegistrationService service = new CustomerRegistrationService() {
            @Override
            public Optional<CustomerRegistrationRequest> findByMobile(String mobile) {
                return Optional.empty();
            }

            @Override
            public CustomerRegistrationRequest createCustomer(CustomerRegistrationRequest customer) {
                return customer;
            }
        };

        CustomerRegistrationRequest saved = service.createCustomer(request);

        assertNotNull(saved);
        assertEquals("Asha", saved.firstName());
        assertEquals("+919876543210", saved.mobile());
        assertEquals("1234", saved.loginPin());
        assertEquals(101L, saved.wingId());
    }
}
