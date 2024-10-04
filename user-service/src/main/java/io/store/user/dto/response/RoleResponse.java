package io.store.user.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Role response DTO")
public class RoleResponse implements Serializable {

    @Schema(description = "ID of the role", example = "1")
    private Long id;

    @Schema(description = "Name of the role", example = "ROLE_ADMIN")
    private String name;

    @Schema(description = "Permission Response of the role", example = "ROLE_ADMIN")
    @JsonBackReference
    private Set<PermissionResponse> permissions;

}