package io.store.steam.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.store.steam.model.Feature;
import io.store.steam.model.Genre;
import io.store.steam.model.SlideImage;
import io.store.steam.model.enums.GameStatus;
import io.store.steam.model.enums.Platform;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Game details")
public class GameResponseDTO {

    @ApiModelProperty(value = "Game ID")
    private UUID id;

    @ApiModelProperty(value = "Game title")
    private String title;

    @ApiModelProperty(value = "Game description")
    private String description;

    @ApiModelProperty(value = "Game price")
    private BigDecimal price;

    @ApiModelProperty(value = "Game release date (YYYY-MM-DD format)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date releaseDate;

    @ApiModelProperty(value = "Game developer name")
    private String developer;

    @ApiModelProperty(value = "Game publisher name")
    private String publisher;

    @ApiModelProperty(value = "Game platform")
    private Platform platform;

    @ApiModelProperty(value = "Game status")
    private GameStatus status;

    @ApiModelProperty(value = "Game stock")
    private int stock;

    @ApiModelProperty(value = "Game discount (percentage)")
    private BigDecimal discount;

    @ApiModelProperty(value = "Game age rating")
    private String ageRating;

    @ApiModelProperty(value = "List of associated genre names")
    private List<GenreResponseDTO> genres;

    @ApiModelProperty(value = "List of associated feature names")
    private List<FeatureResponseDTO> features;

    @ApiModelProperty(value = "List of slide image URLs")
    private List<SlideImageResponseDTO> slideImages;

    @ApiModelProperty(value = "Game create at")
    private LocalDate creatAt;

    @ApiModelProperty(value = "Game update at")
    private LocalDate updateAt;

}
