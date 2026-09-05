package com.asquare.main.repository;

import com.asquare.main.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByMobileAndActiveTrue(String mobile);

    boolean existsByMobileAndActiveTrue(String mobile);

    boolean existsByWingIdAndFlatNumberAndActiveTrue(Long wingId, String flatNumber);
}
