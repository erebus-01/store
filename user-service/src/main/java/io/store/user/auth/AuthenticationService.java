package io.store.user.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.store.user.model.User;
import io.store.user.repository.TokenRepository;
import io.store.user.service.TokenService;
import io.store.user.service.UserService;
import io.store.user.utils.JwtUtil;
import io.store.user.utils.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    @Value("${jwt.timeout}")
    private long timeout;

    @Value("${jwt.refresh.timeout}")
    private long refreshTokenTimeout;

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    public TokenResponse<?> login(AuthenticationRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = (User) authentication.getPrincipal();

        String jwtToken = jwtUtil.generateToken(user, request.getDevice());
        String refreshToken = jwtUtil.generateRefreshToken(user);
        LocalDateTime refreshTokenExpiry = LocalDateTime.now().plusSeconds(refreshTokenTimeout / 1000);

        tokenService.createToken(user, jwtToken, String.valueOf(request.getPlatform()), refreshToken, request.getDevice());
        tokenService.enforceMaxSessions(user);
        log.info("user: {}", user);
        return TokenResponse.builder()
                .status("success")
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .refreshTokenExpiry(refreshTokenExpiry)
                .data(userMapper.toResponse(user))
                .build();
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
            return;
        }

        final String refreshToken = authorization.substring("Bearer ".length());
        final String username = jwtUtil.extractUsername(refreshToken);

        if(StringUtils.isNotEmpty(username)) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            if (jwtUtil.validateToken(refreshToken, userDetails)) {
                var accessToken = jwtUtil.generateToken(userDetails, "mobile");
                tokenService.revokeAllUserTokens((User) userDetails);
                var authResponse = TokenResponse.builder()
                        .status("success")
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .refreshTokenExpiry(LocalDateTime.now().plusSeconds(refreshTokenTimeout / 1000))
                        .data(userDetails)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
