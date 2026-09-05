package com.asquare.main.repository;

import com.asquare.main.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByActiveTrueOrderByProjectNameAsc();

    List<Project> findByBuilderBuilderIdAndActiveTrueOrderByProjectNameAsc(Long builderId);
}
