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
@Table(name = "clusters")
public class Cluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cluster_id")
    private Long clusterId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "cluster_name", nullable = false, length = 100)
    private String clusterName;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    protected Cluster() {
    }

    public Long getClusterId() {
        return clusterId;
    }

    public Project getProject() {
        return project;
    }

    public String getClusterName() {
        return clusterName;
    }

    public boolean isActive() {
        return active;
    }
}
