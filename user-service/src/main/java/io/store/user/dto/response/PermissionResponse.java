package io.store.user.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Permission response DTO")
public class PermissionResponse implements Serializable {
    @Schema(description = "ID of the permission", example = "1")
    private Long id;

    @Schema(description = "Name of the permission", example = "READ_PRIVILEGES")
    private String name;
}
