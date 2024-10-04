package io.store.steam.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Slide Image details")
public class SlideImageResponseDTO {

    @ApiModelProperty(value = "Slide Image url")
    private String imageUrl;

    @ApiModelProperty(value = "Slide Image caption")
    private String caption;

    @ApiModelProperty(value = "Slide Image displayOrder")
    private int displayOrder;

}
