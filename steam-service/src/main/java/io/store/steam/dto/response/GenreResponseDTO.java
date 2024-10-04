package io.store.steam.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Genre details")
public class GenreResponseDTO {
    @ApiModelProperty(value = "Genre of game")
    private String name;
}
