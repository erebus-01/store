package io.store.user.auth;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
public class TokenResponse<T> {

    private String accessToken;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiry;
    private String status;
    private T data;

}
