package io.store.user.service.impl;

import io.store.user.model.Token;
import io.store.user.model.User;
import io.store.user.model.enums.Platform;
import io.store.user.repository.TokenRepository;
import io.store.user.service.TokenService;
import io.store.user.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.timeout}")
    private long timeout;

    @Value("${jwt.refresh.timeout}")
    private long refreshTokenTimeout;


    private final TokenRepository tokenRepository;

    @Override
    public Token createToken(User user, String jwtToken, String platform, String refreshToken, String deviceType) {
        Token token = Token.builder()
                .user(user)
                .jwtToken(jwtToken)
                .jwtTokenExpiry(LocalDateTime.now().plusSeconds(timeout / 1000))
                .refreshToken(refreshToken)
                .refreshTokenExpiry(LocalDateTime.now().plusSeconds(refreshTokenTimeout / 1000))
                .deviceType(deviceType)
                .platform(Platform.valueOf(platform))
                .expired(false)
                .revoked(false)
                .build();
        return tokenRepository.save(token);
    }

    @Override
    public List<Token> getTokensByUser(User user) {
        return tokenRepository.findByUser(user);
    }

    @Override
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void deleteTokenByJwtToken(String jwtToken) {
        tokenRepository.deleteByJwtToken(jwtToken);
    }

    @Override
    public void enforceMaxSessions(User user) {
        List<Token> tokens = tokenRepository.findByUser(user);
        if (tokens.size() > 3) {
            List<Token> nonMobileTokens = tokens.stream()
                    .filter(token ->
                            !token.getPlatform().equals(Platform.ANDROID)
                                    && !token.getPlatform().equals(Platform.IOS))
                    .sorted(Comparator.comparing(Token::getCreatedAt))
                    .toList();

            if (!nonMobileTokens.isEmpty()) {
                tokenRepository.deleteById(nonMobileTokens.get(0).getId());
            } else {
                tokens.sort(Comparator.comparing(Token::getCreatedAt));
                tokenRepository.deleteById(tokens.get(0).getId());
            }
        }
    }
}
