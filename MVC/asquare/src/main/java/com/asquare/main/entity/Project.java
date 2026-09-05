package com.asquare.main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "builder_id", nullable = false)
    private Builder builder;

    @Column(name = "project_name", nullable = false, length = 150)
    private String projectName;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    protected Project() {
    }

    public Long getProjectId() {
        return projectId;
    }

    public Builder getBuilder() {
        return builder;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getCity() {
        return city;
    }

    public boolean isActive() {
        return active;
    }
}
