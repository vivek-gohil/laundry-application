package com.asquare.main.repository;

import com.asquare.main.entity.PickupScheduleOverride;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupScheduleExceptionRepository extends JpaRepository<PickupScheduleOverride, Long> {

    Optional<PickupScheduleOverride> findByExceptionDateAndActiveTrue(LocalDate exceptionDate);
}
