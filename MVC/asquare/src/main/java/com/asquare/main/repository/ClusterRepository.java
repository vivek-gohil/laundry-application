package com.asquare.main.repository;

import com.asquare.main.entity.Cluster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {

    List<Cluster> findByActiveTrueOrderByClusterNameAsc();

    List<Cluster> findByProjectProjectIdAndActiveTrueOrderByClusterNameAsc(Long projectId);
}
