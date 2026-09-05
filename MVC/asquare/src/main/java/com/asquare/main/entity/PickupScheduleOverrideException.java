package com.asquare.main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "pickup_schedule_exception")
public class PickupScheduleOverrideException {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exception_id")
    private Long exceptionId;

    @Column(name = "exception_date", nullable = false)
    private LocalDate exceptionDate;

    @Column(name = "working_day", nullable = false)
    private boolean workingDay;

    @Column(nullable = false)
    private boolean active;

    protected PickupScheduleOverrideException() {
    }

    public boolean isWorkingDay() {
        return workingDay;
    }
}
