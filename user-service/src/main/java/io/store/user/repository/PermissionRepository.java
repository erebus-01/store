package io.store.user.repository;

import io.store.user.model.Permission;
import io.store.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
