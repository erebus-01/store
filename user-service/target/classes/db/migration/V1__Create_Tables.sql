-- V1__Create_Tables.sql

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       phone_number VARCHAR(15) NOT NULL,
                       date_of_birth DATE NOT NULL,
                       gender VARCHAR(10),
                       status VARCHAR(10),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE permissions (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE groups (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE addresses (
                           id SERIAL PRIMARY KEY,
                           street VARCHAR(100) NOT NULL,
                           city VARCHAR(50) NOT NULL,
                           state VARCHAR(50) NOT NULL,
                           postal_code VARCHAR(20) NOT NULL,
                           country VARCHAR(50) NOT NULL,
                           user_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE tokens (
                        id SERIAL PRIMARY KEY,
                        user_id BIGINT NOT NULL REFERENCES users(id),
                        jwt_token VARCHAR(500) NOT NULL,
                        jwt_token_expiry TIMESTAMP NOT NULL,
                        refresh_token VARCHAR(500) NOT NULL,
                        refresh_token_expiry TIMESTAMP NOT NULL,
                        platform VARCHAR(20) NOT NULL,
                        expired BOOLEAN NOT NULL,
                        revoked BOOLEAN NOT NULL,
                        device_type VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE has_user_role (
                               user_id BIGINT NOT NULL REFERENCES users(id),
                               role_id BIGINT NOT NULL REFERENCES roles(id),
                               PRIMARY KEY (user_id, role_id)
);

CREATE TABLE has_user_group (
                                user_id BIGINT NOT NULL REFERENCES users(id),
                                group_id BIGINT NOT NULL REFERENCES groups(id),
                                PRIMARY KEY (user_id, group_id)
);

CREATE TABLE has_role_permissions (
                                      role_id BIGINT NOT NULL REFERENCES roles(id),
                                      permission_id BIGINT NOT NULL REFERENCES permissions(id),
                                      PRIMARY KEY (role_id, permission_id)
);
