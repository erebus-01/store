package io.store.user.repository;

import io.store.user.model.Role;
import io.store.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
