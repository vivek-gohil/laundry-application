package com.laundry.main.servicecatalog.entity;

import java.math.BigDecimal;

import com.laundry.main.audit.BaseAuditableEntity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service_master")
public class ServiceMaster extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "service_name",
            nullable = false,
            unique = true)
    private String serviceName;

    @Column(name = "price",
            nullable = false,
            precision = 10,
            scale = 2)
    private BigDecimal price;

    @Column(name = "active")
    @Builder.Default
    private Boolean active = true;
}
