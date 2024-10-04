package io.store.user.repository;

import io.store.user.model.Address;
import io.store.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
