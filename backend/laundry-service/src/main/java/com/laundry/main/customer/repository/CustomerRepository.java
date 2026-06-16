package com.laundry.main.customer.repository;

import com.laundry.main.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByMobile(String mobile);

    boolean existsByEmail(String email);

    boolean existsByMobileAndCustomerIdNot(String mobile, Long customerId);

    boolean existsByEmailAndCustomerIdNot(String email, Long customerId);
}
