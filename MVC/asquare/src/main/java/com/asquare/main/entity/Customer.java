package com.asquare.main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "wing_id", nullable = false)
    private Long wingId;

    @Column(name = "flat_number", nullable = false)
    private String flatNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile", nullable = false, length = 15)
    private String mobile;

    @Column(name = "login_pin_hash", nullable = false, length = 255)
    private String loginPinHash;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    // Required by JPA for entity instantiation - do not remove
    public Customer() {
        // No-arg constructor needed for JPA to instantiate entities via reflection
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getMobile() {
        return mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getWingId() {
        return wingId;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public String getLoginPinHash() {
        return loginPinHash;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setWingId(Long wingId) {
        this.wingId = wingId;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setLoginPinHash(String loginPinHash) {
        this.loginPinHash = loginPinHash;
    }
}
