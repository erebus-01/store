package io.store.steam.dto.request;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Feature request DTO")
public class FeatureRequestDTO {
    @NotBlank(message = "Feature name is required")
    private String name;
}
