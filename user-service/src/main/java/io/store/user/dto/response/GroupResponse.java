package io.store.user.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Group response DTO")
public class GroupResponse implements Serializable {
    @Schema(description = "ID of the group", example = "1")
    private Long id;

    @Schema(description = "Name of the group", example = "Admins")
    private String name;

    @Schema(description = "Role Response with the group", example = "[1, 2]")
    @JsonBackReference
    private Set<RoleResponse> roles;
}