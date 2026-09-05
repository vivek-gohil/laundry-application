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
@Table(name = "wings")
public class Wing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wing_id")
    private Long wingId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cluster_id", nullable = false)
    private Cluster cluster;

    @Column(name = "wing_name", nullable = false, length = 50)
    private String wingName;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    protected Wing() {
    }

    public Long getWingId() {
        return wingId;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public String getWingName() {
        return wingName;
    }

    public boolean isActive() {
        return active;
    }
}
