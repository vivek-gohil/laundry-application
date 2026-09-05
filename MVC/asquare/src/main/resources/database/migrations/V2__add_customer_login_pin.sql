-- Run once on existing A Square databases before deploying the PIN login feature.
ALTER TABLE customers ADD COLUMN login_pin_hash VARCHAR(255) NULL AFTER mobile;

-- Existing accounts have no PIN until a PIN-reset flow is completed. New registrations
-- always store a BCrypt hash, as required by the application entity mapping.
