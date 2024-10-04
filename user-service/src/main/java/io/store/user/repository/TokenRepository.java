package io.store.user.repository;

import io.store.user.model.Token;
import io.store.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);
    Optional<Token> findByJwtToken(String jwtToken);
    List<Token> findAllValidTokenByUser(User user);
    void deleteByJwtToken(String jwtToken);
}
