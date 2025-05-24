-- V1__Create_Account_Table.sql

-- Drop table if it exists
DROP TABLE IF EXISTS "ms-data-master-account".t_acct;

-- Drop indexes if they exist
DROP INDEX IF EXISTS idx_acct_name;
DROP INDEX IF EXISTS idx_email;
DROP INDEX IF EXISTS idx_is_active;
DROP INDEX IF EXISTS idx_created_at;
DROP INDEX IF EXISTS idx_updated_at;

-- Create the table
CREATE TABLE "ms-data-master-account".t_acct (
                        id UUID PRIMARY KEY,
                        acct_name VARCHAR(255) NOT NULL UNIQUE,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        acct_type VARCHAR(255),
                        role_type VARCHAR(255),
                        email VARCHAR(255) NOT NULL UNIQUE,
                        website VARCHAR(255),
                        password VARCHAR(255) NOT NULL,
                        phone_number VARCHAR(255) NOT NULL,
                        location VARCHAR(255),
                        is_active BOOLEAN DEFAULT TRUE,
                        billing_details JSONB,
                        credit_term_details JSONB,
                        business_details JSONB,
                        company_details JSONB,
                        role_id UUID,
                        version INTEGER,
                        created_by VARCHAR(255),
                        created_at TIMESTAMP,
                        updated_by VARCHAR(255),
                        updated_at TIMESTAMP,
                        deleted_by VARCHAR(255),
                        deleted_at TIMESTAMP,
                        CONSTRAINT fk_role
                            FOREIGN KEY (role_id)
                                REFERENCES t_roles(id)
);

-- Create indexes for frequently queried columns
CREATE INDEX idx_acct_name ON "ms-data-master-account".t_acct(acct_name);
CREATE INDEX idx_email ON "ms-data-master-account".t_acct(email);
CREATE INDEX idx_is_active ON "ms-data-master-account".t_acct(is_active);
CREATE INDEX idx_created_at ON "ms-data-master-account".t_acct(created_at);
CREATE INDEX idx_updated_at ON "ms-data-master-account".t_acct(updated_at);


