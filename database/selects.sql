USE asquare_laundry;


SELECT * FROM builders;
SELECT * FROM projects;
SELECT * FROM clusters;
SELECT * FROM wings;
SELECT * FROM customers;

-- ============================================================
-- 1. BUILDERS
-- ============================================================

SELECT
    builder_id,
    builder_name,
    active,
    created_at,
    created_by,
    updated_at,
    updated_by
FROM builders
ORDER BY builder_name;


-- ============================================================
-- 2. PROJECTS
-- ============================================================

SELECT
    p.project_id,
    p.builder_id,
    b.builder_name,
    p.project_name,
    p.city,
    p.active,
    p.created_at,
    p.created_by,
    p.updated_at,
    p.updated_by
FROM projects p
INNER JOIN builders b
    ON b.builder_id = p.builder_id
ORDER BY b.builder_name, p.project_name;


-- ============================================================
-- 3. CLUSTERS
-- ============================================================

SELECT
    c.cluster_id,
    c.project_id,
    p.project_name,
    c.cluster_name,
    c.active,
    c.created_at,
    c.created_by,
    c.updated_at,
    c.updated_by
FROM clusters c
INNER JOIN projects p
    ON p.project_id = c.project_id
ORDER BY p.project_name, c.cluster_name;


-- ============================================================
-- 4. WINGS
-- ============================================================

SELECT
    w.wing_id,
    w.cluster_id,
    c.cluster_name,
    p.project_name,
    w.wing_name,
    w.active,
    w.created_at,
    w.created_by,
    w.updated_at,
    w.updated_by
FROM wings w
INNER JOIN clusters c
    ON c.cluster_id = w.cluster_id
INNER JOIN projects p
    ON p.project_id = c.project_id
ORDER BY p.project_name, c.cluster_name, w.wing_name;


-- ============================================================
-- 5. CUSTOMERS
-- ============================================================

SELECT
    cu.customer_id,
    cu.first_name,
    cu.last_name,
    cu.mobile,
    cu.email,
    cu.flat_number,
    w.wing_name,
    c.cluster_name,
    p.project_name,
    b.builder_name,
    p.city,
    cu.active,
    cu.created_at,
    cu.created_by,
    cu.updated_at,
    cu.updated_by
FROM customers cu
INNER JOIN wings w
    ON w.wing_id = cu.wing_id
INNER JOIN clusters c
    ON c.cluster_id = w.cluster_id
INNER JOIN projects p
    ON p.project_id = c.project_id
INNER JOIN builders b
    ON b.builder_id = p.builder_id
ORDER BY
    b.builder_name,
    p.project_name,
    c.cluster_name,
    w.wing_name,
    cu.flat_number;
    
USE asquare_laundry;

-- Pickup Slot Master
SELECT * FROM pickup_slot_master;

-- Pickup Schedule
SELECT * FROM pickup_schedule;

-- Pickup Schedule Exception
SELECT * FROM pickup_schedule_exception;

-- Pickup Bookings
SELECT * FROM pickup_bookings;

SELECT * FROM customers;
SELECT * FROM customer_otp;


    