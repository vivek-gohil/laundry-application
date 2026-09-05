package com.asquare.main.repository;

import com.asquare.main.entity.PickupSlotMaster;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface PickupSlotMasterRepository extends JpaRepository<PickupSlotMaster, Long> {

    List<PickupSlotMaster> findByActiveTrueOrderByStartTimeAsc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PickupSlotMaster> findBySlotMasterIdAndActiveTrue(Long slotMasterId);
}
