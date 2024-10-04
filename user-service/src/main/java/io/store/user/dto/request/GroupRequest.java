package io.store.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Address request DTO")
public class GroupRequest {
    @Schema(description = "Name of the group", example = "Admins")
    @NotBlank(message = "Group name is required")
    private String name;

    @Schema(description = "IDs of roles associated with the group", example = "[1, 2]")
    private Set<Long> roles;
}
