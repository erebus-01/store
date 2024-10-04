-- V2__create_group_roles_table.sql

CREATE TABLE has_group_roles (
                             group_id BIGINT NOT NULL,
                             role_id BIGINT NOT NULL,
                             PRIMARY KEY (group_id, role_id),
                             FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
                             FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);