package io.store.user.repository;

import io.store.user.model.Group;
import io.store.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
