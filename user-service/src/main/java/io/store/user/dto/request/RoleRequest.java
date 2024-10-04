package io.store.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Role request DTO")
public class RoleRequest {
    @Schema(description = "Name of the role", example = "ROLE_ADMIN")
    @NotBlank(message = "Role name is required")
    private String name;

    @Schema(description = "IDs of permission", example = "1, 2")
    private Set<Long> permissions;
}
