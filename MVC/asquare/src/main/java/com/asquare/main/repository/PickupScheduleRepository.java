package com.asquare.main.repository;

import com.asquare.main.entity.PickupSchedule;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupScheduleRepository extends JpaRepository<PickupSchedule, Long> {

    Optional<PickupSchedule> findByDayOfWeekAndActiveTrue(Integer dayOfWeek);
}
