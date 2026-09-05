

-- ============================================================
-- A SQUARE LAUNDRY
-- Builder → Project → Cluster → Wing → Customer
-- MySQL 8+
-- ============================================================

CREATE DATABASE IF NOT EXISTS asquare_laundry
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE asquare_laundry;


-- ============================================================
-- 1. BUILDERS
-- ============================================================

CREATE TABLE builders (
    builder_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    builder_name VARCHAR(150) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT UNSIGNED NULL,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT UNSIGNED NULL,
    PRIMARY KEY (builder_id),
    CONSTRAINT uk_builders_name UNIQUE (builder_name),
    CONSTRAINT chk_builders_name CHECK (CHAR_LENGTH(TRIM(builder_name)) >= 2)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

ALTER TABLE builders
    MODIFY builder_name VARCHAR(150) NOT NULL,
    MODIFY created_by VARCHAR(50) NULL,
    MODIFY updated_by VARCHAR(50) NULL;

-- ============================================================
-- 2. PROJECTS
-- ============================================================
CREATE TABLE projects (
    project_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    builder_id BIGINT UNSIGNED NOT NULL,
    project_name VARCHAR(150) NOT NULL,
    city VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT UNSIGNED NULL,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT UNSIGNED NULL,
    PRIMARY KEY (project_id),
    CONSTRAINT fk_projects_builder FOREIGN KEY (builder_id) REFERENCES builders(builder_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT uk_projects_builder_name UNIQUE (builder_id, project_name),
    CONSTRAINT chk_projects_name CHECK (CHAR_LENGTH(TRIM(project_name)) >= 2),
    CONSTRAINT chk_projects_city CHECK (CHAR_LENGTH(TRIM(city)) >= 2),
    INDEX idx_projects_builder (builder_id),
    INDEX idx_projects_active (active),
    INDEX idx_projects_city (city)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;
  
ALTER TABLE projects
    MODIFY created_by VARCHAR(50) NULL,
    MODIFY updated_by VARCHAR(50) NULL;
-- ============================================================
-- 3. CLUSTERS
-- ============================================================

CREATE TABLE clusters (
    cluster_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    project_id BIGINT UNSIGNED NOT NULL,
    cluster_name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT UNSIGNED NULL,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT UNSIGNED NULL,
    PRIMARY KEY (cluster_id),
    CONSTRAINT fk_clusters_project FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT uk_clusters_project_name UNIQUE (project_id, cluster_name),
    CONSTRAINT chk_clusters_name CHECK (CHAR_LENGTH(TRIM(cluster_name)) >= 1),
    INDEX idx_clusters_project (project_id),
    INDEX idx_clusters_active (active)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

ALTER TABLE clusters
    MODIFY created_by VARCHAR(50) NULL,
    MODIFY updated_by VARCHAR(50) NULL;
-- ============================================================
-- 4. WINGS
-- ============================================================

CREATE TABLE wings (
    wing_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    cluster_id BIGINT UNSIGNED NOT NULL,
    wing_name VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT UNSIGNED NULL,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT UNSIGNED NULL,
    PRIMARY KEY (wing_id),
    CONSTRAINT fk_wings_cluster FOREIGN KEY (cluster_id) REFERENCES clusters(cluster_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT uk_wings_cluster_name UNIQUE (cluster_id, wing_name),
    CONSTRAINT chk_wings_name CHECK (CHAR_LENGTH(TRIM(wing_name)) >= 1),
    INDEX idx_wings_cluster (cluster_id),
    INDEX idx_wings_active (active)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

ALTER TABLE wings
    MODIFY created_by VARCHAR(50) NULL,
    MODIFY updated_by VARCHAR(50) NULL;

-- ============================================================
-- 5. CUSTOMERS
-- ============================================================

    

    
CREATE TABLE customers (
    customer_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NULL,
    wing_id BIGINT UNSIGNED NOT NULL,
    flat_number VARCHAR(20) NOT NULL,
    email VARCHAR(150) NULL,
    mobile VARCHAR(15) NOT NULL,
    login_pin_hash VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT UNSIGNED NULL,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT UNSIGNED NULL,
    PRIMARY KEY (customer_id),
    -- Wing must exist
    CONSTRAINT fk_customers_wing FOREIGN KEY (wing_id) REFERENCES wings(wing_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    -- One customer per flat
    CONSTRAINT uk_customers_wing_flat UNIQUE (wing_id, flat_number),
    -- One account per mobile number
    CONSTRAINT uk_customers_mobile UNIQUE (mobile),
    -- One account per email where email is provided
    CONSTRAINT uk_customers_email UNIQUE (email),
    CONSTRAINT chk_customers_first_name CHECK (CHAR_LENGTH(TRIM(first_name)) >= 2),
    CONSTRAINT chk_customers_mobile CHECK (CHAR_LENGTH(mobile) BETWEEN 10 AND 15),
    CONSTRAINT chk_customers_flat_number CHECK (CHAR_LENGTH(TRIM(flat_number)) >= 1),
    INDEX idx_customers_wing (wing_id),
    INDEX idx_customers_active (active),
    INDEX idx_customers_location (wing_id, flat_number)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

ALTER TABLE customers
    MODIFY email VARCHAR(254) NULL,
    MODIFY mobile VARCHAR(15) NOT NULL,
    MODIFY created_by VARCHAR(50) NULL,
    MODIFY updated_by VARCHAR(50) NULL;
    
ALTER TABLE customers
DROP CONSTRAINT chk_customers_mobile;

ALTER TABLE customers
ADD CONSTRAINT chk_customers_mobile CHECK ( mobile REGEXP '^\\+[1-9][0-9]{9,14}$');


CREATE TABLE pickup_slot_master (
    slot_master_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    capacity INT UNSIGNED NOT NULL DEFAULT 10,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NULL,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) NULL,
    PRIMARY KEY (slot_master_id),
    CONSTRAINT uk_pickup_slot_master_time UNIQUE (start_time, end_time),
    CONSTRAINT chk_pickup_slot_master_time CHECK (end_time > start_time),
    CONSTRAINT chk_pickup_slot_master_capacity CHECK (capacity > 0)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

CREATE TABLE pickup_schedule (
    schedule_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    day_of_week TINYINT UNSIGNED NOT NULL,
    working_day BOOLEAN NOT NULL DEFAULT TRUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NULL,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) NULL,
    PRIMARY KEY (schedule_id),
    CONSTRAINT uk_pickup_schedule_day UNIQUE (day_of_week),
    CONSTRAINT chk_pickup_schedule_day CHECK (day_of_week BETWEEN 1 AND 7)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

CREATE TABLE pickup_schedule_exception (
    exception_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    exception_date DATE NOT NULL,
    working_day BOOLEAN NOT NULL,
    reason VARCHAR(255) NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NULL,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) NULL,
    PRIMARY KEY (exception_id),
    CONSTRAINT uk_pickup_exception_date UNIQUE (exception_date)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci; 
  
  CREATE TABLE pickup_bookings (
    booking_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    customer_id BIGINT UNSIGNED NOT NULL,
    slot_master_id BIGINT UNSIGNED NOT NULL,
    pickup_date DATE NOT NULL,
    booking_status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
    booked_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NULL,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) NULL,
    PRIMARY KEY (booking_id),
    CONSTRAINT fk_pickup_bookings_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_pickup_bookings_slot FOREIGN KEY (slot_master_id) REFERENCES pickup_slot_master(slot_master_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    -- One customer cannot have two bookings
    -- for the same date and slot
    CONSTRAINT uk_pickup_booking_customer_slot UNIQUE (customer_id, pickup_date, slot_master_id),
    CONSTRAINT chk_pickup_booking_status CHECK ( booking_status IN ( 'CONFIRMED','CANCELLED','COMPLETED')),
    INDEX idx_pickup_bookings_date_slot (pickup_date, slot_master_id),
    INDEX idx_pickup_bookings_customer (customer_id),
    INDEX idx_pickup_bookings_status (booking_status)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;
