INSERT INTO t_roles ( id, roles_name, roles_description, version, status, created_by, created_at)
VALUES
    ('c4f8ac9a-8f87-4a67-9b79-70c3b944f3c5', 'SUPER_ADMIN', 'Super Admin', 1, 'ACTIVE', 'system', '2024-11-24 22:39:16'),
    ('c4f8ac9a-8f87-4a67-9b79-70c3b944f3c4', 'ADMIN', 'Admin', 1, 'ACTIVE', 'system', '2024-11-24 22:39:18'),
    ('c4f8ac9a-8f87-4a67-9b79-70c3b944f3c3', 'USER', 'User', 1, 'ACTIVE', 'system', '2024-11-24 22:39:19')
    ON CONFLICT (id) DO NOTHING;
