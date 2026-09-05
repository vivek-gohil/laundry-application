package com.asquare.main.repository;

import com.asquare.main.entity.Builder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuilderRepository extends JpaRepository<Builder, Long> {

    List<Builder> findByActiveTrueOrderByBuilderNameAsc();
}
