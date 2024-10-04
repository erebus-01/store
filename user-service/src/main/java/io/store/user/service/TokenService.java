package io.store.user.service;

import io.store.user.model.Token;
import io.store.user.model.User;

import java.util.List;

public interface TokenService {
    Token createToken(User user, String jwtToken, String platform, String refreshToken, String deviceType);
    List<Token> getTokensByUser(User user);
    void revokeAllUserTokens(User user);
    void deleteTokenByJwtToken(String jwtToken);
    void enforceMaxSessions(User user);
}
