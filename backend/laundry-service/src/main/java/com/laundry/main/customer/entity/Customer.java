package com.laundry.main.customer.entity;

import com.laundry.main.audit.BaseAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "customer", indexes = {
        @Index(name = "idx_customer_mobile", columnList = "mobile"),
        @Index(name = "idx_customer_email", columnList = "email")
})
public class Customer extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "mobile", nullable = false, unique = true, length = 20)
    private String mobile;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "flat_number", nullable = false, length = 50)
    private String flatNumber;

    @Column(name = "building_name", nullable = false, length = 150)
    private String buildingName;

    @Column(name = "address_line_1", nullable = false, length = 255)
    private String addressLine1;

    @Column(name = "address_line_2", length = 255)
    private String addressLine2;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "pincode", nullable = false, length = 10)
    private String pincode;

    @Column(name = "landmark", length = 255)
    private String landmark;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
