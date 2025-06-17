INSERT INTO t_acct_type ( id, name, dscp, version, status, created_by, created_at, updated_by, updated_at, deleted_at, deleted_by)
VALUES
    ('c4f8ac9a-8f87-4a67-9b79-70c3b944f3a5', 'CUSTOMER', 'Super Admin', 1, 'ACTIVE', 'system', '2024-11-24 22:39:16', NULL, NULL, NULL, NULL),
    ('c4f8ac9a-8f87-4a67-9b79-70c3b944f3a4', 'SUPPLIER', 'Admin', 1, 'ACTIVE', 'system', '2024-11-24 22:39:18', NULL, NULL, NULL, NULL),
    ('c4f8ac9a-8f87-4a67-9b79-70c3b944f3a3', 'CORPORATE', 'User', 1, 'ACTIVE', 'system', '2024-11-24 22:39:19', NULL, NULL, NULL, NULL)
    ON CONFLICT (id) DO NOTHING;
