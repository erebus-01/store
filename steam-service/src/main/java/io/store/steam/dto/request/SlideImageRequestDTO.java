package io.store.steam.dto.request;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlideImageRequestDTO implements Serializable {
    @ApiModelProperty(value = "Image URL", required = true)
    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @ApiModelProperty(value = "Caption")
    private String caption;

    @ApiModelProperty(value = "Display Order when show UI")
    @Min(value = 1, message = "Display image cannot be negative")
    private int displayOrder;
}
