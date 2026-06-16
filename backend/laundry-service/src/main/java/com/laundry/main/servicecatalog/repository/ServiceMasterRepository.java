package com.laundry.main.servicecatalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laundry.main.servicecatalog.entity.ServiceMaster;

public interface ServiceMasterRepository
        extends JpaRepository<ServiceMaster, Long> {

    Optional<ServiceMaster> findByServiceName(String serviceName);

    boolean existsByServiceName(String serviceName);

    boolean existsByServiceNameAndServiceIdNot(String serviceName, Long serviceId);
}
