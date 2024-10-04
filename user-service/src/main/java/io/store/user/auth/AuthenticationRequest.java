package io.store.user.auth;

import io.store.user.model.enums.Platform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {

    private String username;
    private String password;
    private String device;
    private Platform platform;
    private String version;
    private String deviceToken;

}
