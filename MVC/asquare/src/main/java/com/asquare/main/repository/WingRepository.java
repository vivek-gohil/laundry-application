package com.asquare.main.repository;

import com.asquare.main.entity.Wing;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WingRepository extends JpaRepository<Wing, Long> {

    List<Wing> findByActiveTrueOrderByWingNameAsc();

    List<Wing> findByClusterClusterIdAndActiveTrueOrderByWingNameAsc(Long clusterId);
}
