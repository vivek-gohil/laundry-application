package com.laundry.main.repository;

import com.laundry.main.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByMobile(String mobile);

    boolean existsByEmail(String email);

    boolean existsByMobileAndCustomerIdNot(String mobile, Long customerId);

    boolean existsByEmailAndCustomerIdNot(String email, Long customerId);
}
