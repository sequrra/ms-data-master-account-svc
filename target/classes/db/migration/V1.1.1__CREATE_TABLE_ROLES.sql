-- V1__Create_Roles_Table.sql

-- Drop table if it exists
DROP TABLE IF EXISTS "ms-data-master-account".t_roles;

-- Drop indexes if they exist
DROP INDEX IF EXISTS idx_created_at;
DROP INDEX IF EXISTS idx_updated_at;

-- Create the table
CREATE TABLE "ms-data-master-account".t_roles (
                        id UUID PRIMARY KEY,
                        roles_name VARCHAR(255) NOT NULL,
                        roles_description VARCHAR(255) NOT NULL,
                        version INTEGER,
                        status VARCHAR(50),
                        created_by VARCHAR(255),
                        created_at TIMESTAMP,
                        updated_by VARCHAR(255),
                        updated_at TIMESTAMP,
                        deleted_by VARCHAR(255),
                        deleted_at TIMESTAMP
);

-- Create indexes for frequently queried columns
CREATE INDEX idx_created_at ON "ms-data-master-account".t_roles(created_at);
CREATE INDEX idx_updated_at ON "ms-data-master-account".t_roles(updated_at);

