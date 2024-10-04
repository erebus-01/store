package io.store.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Permission request DTO")
public class PermissionRequest {
    @Schema(description = "Name of the permission", example = "READ_PRIVILEGES")
    @NotBlank(message = "Permission name is required")
    private String name;
}
