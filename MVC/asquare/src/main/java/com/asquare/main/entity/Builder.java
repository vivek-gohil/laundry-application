package com.asquare.main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "builders")
public class Builder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "builder_id")
    private Long builderId;

    @Column(name = "builder_name", nullable = false, length = 150)
    private String builderName;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    protected Builder() {
    }

    public Long getBuilderId() {
        return builderId;
    }

    public String getBuilderName() {
        return builderName;
    }

    public boolean isActive() {
        return active;
    }
}
