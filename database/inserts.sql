USE asquare_laundry;

-- ============================================================
-- 1. BUILDERS
-- ============================================================

INSERT INTO builders
    (builder_name, active, created_by, updated_by)
VALUES
    ('Arihant Developers', TRUE, 1, 1),
    ('Lodha Group', TRUE, 1, 1),
    ('Godrej Properties', TRUE, 1, 1);


-- ============================================================
-- 2. PROJECTS
-- ============================================================

INSERT INTO projects
    (builder_id, project_name, city, active, created_by, updated_by)
VALUES
    (1, 'Arihant Anaika', 'Panvel', TRUE, 1, 1),
    (1, 'Arihant Aspire', 'Navi Mumbai', TRUE, 1, 1),
    (2, 'Lodha Crown', 'Dombivli', TRUE, 1, 1),
    (3, 'Godrej City', 'Panvel', TRUE, 1, 1);


-- ============================================================
-- 3. CLUSTERS
-- ============================================================

INSERT INTO clusters
    (project_id, cluster_name, active, created_by, updated_by)
VALUES
    (1, 'Cluster A', TRUE, 1, 1),
    (1, 'Cluster B', TRUE, 1, 1),

    (2, 'Cluster A', TRUE, 1, 1),

    (3, 'Cluster A', TRUE, 1, 1),
    (3, 'Cluster B', TRUE, 1, 1),

    (4, 'Cluster A', TRUE, 1, 1);


-- ============================================================
-- 4. WINGS
-- ============================================================

INSERT INTO wings
    (cluster_id, wing_name, active, created_by, updated_by)
VALUES
    -- Arihant Anaika - Cluster A
    (1, 'Wing A', TRUE, 1, 1),
    (1, 'Wing B', TRUE, 1, 1),

    -- Arihant Anaika - Cluster B
    (2, 'Wing A', TRUE, 1, 1),

    -- Arihant Aspire - Cluster A
    (3, 'Wing A', TRUE, 1, 1),

    -- Lodha Crown - Cluster A
    (4, 'Wing A', TRUE, 1, 1),
    (4, 'Wing B', TRUE, 1, 1),

    -- Lodha Crown - Cluster B
    (5, 'Wing A', TRUE, 1, 1),

    -- Godrej City - Cluster A
    (6, 'Wing A', TRUE, 1, 1);


-- ============================================================
-- 5. CUSTOMERS
-- ============================================================

INSERT INTO customers
    (
        first_name,
        last_name,
        wing_id,
        flat_number,
        email,
        mobile,
        active,
        created_by,
        updated_by
    )
VALUES

    -- Arihant Anaika / Cluster A / Wing A
    ('Vivek', 'Gohil', 1, '101',
        'vivek.gohil@example.com',
        '9876500001',
        TRUE, 1, 1),

    ('Rahul', 'Sharma', 1, '102',
        'rahul.sharma@example.com',
        '9876500002',
        TRUE, 1, 1),

    -- Arihant Anaika / Cluster A / Wing B
    ('Amit', 'Patil', 2, '201',
        'amit.patil@example.com',
        '9876500003',
        TRUE, 1, 1),

    ('Neha', 'Joshi', 2, '202',
        'neha.joshi@example.com',
        '9876500004',
        TRUE, 1, 1),

    -- Arihant Anaika / Cluster B / Wing A
    ('Rohan', 'Mehta', 3, '301',
        'rohan.mehta@example.com',
        '9876500005',
        TRUE, 1, 1),

    -- Arihant Aspire / Cluster A / Wing A
    ('Priya', 'Deshmukh', 4, '401',
        'priya.deshmukh@example.com',
        '9876500006',
        TRUE, 1, 1),

    ('Sanjay', 'Kulkarni', 4, '402',
        'sanjay.kulkarni@example.com',
        '9876500007',
        TRUE, 1, 1),

    -- Lodha Crown / Cluster A / Wing A
    ('Pooja', 'Shah', 5, '501',
        'pooja.shah@example.com',
        '9876500008',
        TRUE, 1, 1),

    -- Lodha Crown / Cluster A / Wing B
    ('Kunal', 'Verma', 6, '601',
        'kunal.verma@example.com',
        '9876500009',
        TRUE, 1, 1),

    -- Godrej City / Cluster A / Wing A
    ('Sneha', 'Iyer', 8, '701',
        'sneha.iyer@example.com',
        '9876500010',
        TRUE, 1, 1);
        
        
USE asquare_laundry;


-- ============================================================
-- 1. PICKUP SLOT MASTER
-- ============================================================
-- Standard pickup slots used every working day
-- ============================================================
TRUNCATE TABLE pickup_slot_master;
INSERT INTO pickup_slot_master
(
    start_time,
    end_time,
    capacity,
    active,
    created_by,
    updated_by
)
VALUES
(
    '09:00:00',
    '11:00:00',
    10,
    TRUE,
    'SYSTEM',
    'SYSTEM'
),
(
    '11:00:00',
    '13:00:00',
    10,
    TRUE,
    'SYSTEM',
    'SYSTEM'
),
(
    '15:00:00',
    '17:00:00',
    10,
    TRUE,
    'SYSTEM',
    'SYSTEM'
),
(
    '17:00:00',
    '19:00:00',
    10,
    TRUE,
    'SYSTEM',
    'SYSTEM'
);


-- ============================================================
-- 2. PICKUP SCHEDULE
-- ============================================================
-- MySQL DAY_OF_WEEK convention used by our application:
--
-- 1 = Monday
-- 2 = Tuesday
-- 3 = Wednesday
-- 4 = Thursday
-- 5 = Friday
-- 6 = Saturday
-- 7 = Sunday
--
-- Sunday is weekly off.
-- ============================================================

INSERT INTO pickup_schedule
(
    day_of_week,
    working_day,
    active,
    created_by,
    updated_by
)
VALUES
(
    1,
    TRUE,
    TRUE,
    'SYSTEM',
    'SYSTEM'
),
(
    2,
    TRUE,
    TRUE,
    'SYSTEM',
    'SYSTEM'
),
(
    3,
    TRUE,
    TRUE,
    'SYSTEM',
    'SYSTEM'
),
(
    4,
    TRUE,
    TRUE,
    'SYSTEM',
    'SYSTEM'
),
(
    5,
    TRUE,
    TRUE,
    'SYSTEM',
    'SYSTEM'
),
(
    6,
    TRUE,
    TRUE,
    'SYSTEM',
    'SYSTEM'
),
(
    7,
    FALSE,
    TRUE,
    'SYSTEM',
    'SYSTEM'
);


-- ============================================================
-- 3. PICKUP SCHEDULE EXCEPTION
-- ============================================================
-- Examples of holidays / leave / special working days
--
-- working_day = FALSE → No pickup
-- working_day = TRUE  → Pickup available even if normally off
-- ============================================================

INSERT INTO pickup_schedule_exception
(
    exception_date,
    working_day,
    reason,
    active,
    created_by,
    updated_by
)
VALUES
(
    '2026-09-15',
    FALSE,
    'Staff Leave',
    TRUE,
    'SYSTEM',
    'SYSTEM'
),
(
    '2026-10-02',
    FALSE,
    'Public Holiday',
    TRUE,
    'SYSTEM',
    'SYSTEM'
),
(
    '2026-10-25',
    TRUE,
    'Special Working Day',
    TRUE,
    'SYSTEM',
    'SYSTEM'
);


-- ============================================================
-- 4. PICKUP BOOKINGS
-- ============================================================
-- Sample bookings for testing availability.
--
-- Assumes customer_id 1-10 already exist
-- and pickup_slot_master IDs 1-4 were generated above.
-- ============================================================

INSERT INTO pickup_bookings
(
    customer_id,
    slot_master_id,
    pickup_date,
    booking_status,
    created_by,
    updated_by
)
VALUES
-- 28-Aug-2026 / 09:00-11:00
(
    1,
    1,
    '2026-08-28',
    'CONFIRMED',
    'SYSTEM',
    'SYSTEM'
),
(
    2,
    1,
    '2026-08-28',
    'CONFIRMED',
    'SYSTEM',
    'SYSTEM'
),
(
    3,
    1,
    '2026-08-28',
    'CONFIRMED',
    'SYSTEM',
    'SYSTEM'
),

-- 28-Aug-2026 / 11:00-13:00
(
    4,
    2,
    '2026-08-28',
    'CONFIRMED',
    'SYSTEM',
    'SYSTEM'
),
(
    5,
    2,
    '2026-08-28',
    'CONFIRMED',
    'SYSTEM',
    'SYSTEM'
),

-- 28-Aug-2026 / 15:00-17:00
(
    6,
    3,
    '2026-08-28',
    'CONFIRMED',
    'SYSTEM',
    'SYSTEM'
),

-- 28-Aug-2026 / 17:00-19:00
(
    7,
    4,
    '2026-08-28',
    'CONFIRMED',
    'SYSTEM',
    'SYSTEM'
),
(
    8,
    4,
    '2026-08-28',
    'CONFIRMED',
    'SYSTEM',
    'SYSTEM'
);

-- Insert script for testing slot availability

USE asquare_laundry;

INSERT INTO pickup_bookings
(
    customer_id,
    slot_master_id,
    pickup_date,
    booking_status,
    created_by,
    updated_by
)
VALUES

-- ============================================================
-- 28-Aug-2026
-- Slot 1: 09:00 - 11:00
-- Booked: 5 / 10
-- ============================================================

(1, 1, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(2, 1, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(3, 1, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(4, 1, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(5, 1, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),


-- ============================================================
-- 28-Aug-2026
-- Slot 2: 11:00 - 13:00
-- Booked: 3 / 10
-- ============================================================

(6, 2, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(7, 2, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(8, 2, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),


-- ============================================================
-- 28-Aug-2026
-- Slot 3: 15:00 - 17:00
-- Booked: 8 / 10
-- ============================================================

(1, 3, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(2, 3, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(3, 3, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(4, 3, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(5, 3, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(6, 3, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(7, 3, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(8, 3, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),


-- ============================================================
-- 28-Aug-2026
-- Slot 4: 17:00 - 19:00
-- Booked: 10 / 10
-- FULL
-- ============================================================

(1, 4, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(2, 4, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(3, 4, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(4, 4, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(5, 4, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(6, 4, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(7, 4, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(8, 4, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(9, 4, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(10, 4, '2026-08-28', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),


-- ============================================================
-- 29-Aug-2026
-- Slot 1: 09:00 - 11:00
-- Booked: 1 / 10
-- ============================================================

(1, 1, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),


-- ============================================================
-- 29-Aug-2026
-- Slot 2: 11:00 - 13:00
-- Booked: 9 / 10
-- ============================================================

(1, 2, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(2, 2, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(3, 2, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(4, 2, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(5, 2, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(6, 2, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(7, 2, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(8, 2, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(9, 2, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),


-- ============================================================
-- 29-Aug-2026
-- Slot 3: 15:00 - 17:00
-- Booked: 5 / 10
-- ============================================================

(1, 3, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(2, 3, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(3, 3, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(4, 3, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(5, 3, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),


-- ============================================================
-- 29-Aug-2026
-- Slot 4: 17:00 - 19:00
-- Booked: 5 / 10
-- ============================================================

(6, 4, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(7, 4, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(8, 4, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(9, 4, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM'),
(10, 4, '2026-08-29', 'CONFIRMED', 'SYSTEM', 'SYSTEM');


-- Verify the inserted data
SELECT
    slot_master_id,
    pickup_date,
    booking_status,
    COUNT(*) AS booked_count
FROM pickup_bookings
WHERE pickup_date IN ('2026-08-28', '2026-08-29')
  AND booking_status = 'CONFIRMED'
GROUP BY
    pickup_date,
    slot_master_id
ORDER BY
    pickup_date,
    slot_master_id;
    
INSERT INTO pickup_schedule_exception
(
    exception_date,
    working_day,
    reason,
    active,
    created_by,
    updated_by
)
VALUES
(
    '2026-08-30',
    TRUE,
    'Working Sunday',
    TRUE,
    'SYSTEM',
    'SYSTEM'
);
