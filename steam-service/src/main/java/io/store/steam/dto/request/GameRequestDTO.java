package io.store.steam.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.store.steam.model.enums.Country;
import io.store.steam.model.enums.GameStatus;
import io.store.steam.model.enums.Platform;
import io.store.steam.utils.EnumPattern;
import io.store.steam.utils.ValidEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Game create request DTO")
public class GameRequestDTO implements Serializable {

    @Schema(description = "Game title")
    @NotBlank(message = "Title is required")
    private String title;

    @ApiModelProperty(required = true, value = "Game description")
    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    private String description;

    @ApiModelProperty(required = true, value = "Game title")
    @NotBlank(message = "Title is required")
    private String imageUrl;

    @ApiModelProperty(required = true, value = "Game price")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be positive")
    private BigDecimal price;

    @ApiModelProperty(value = "Game release date (YYYY-MM-DD format)", example = "2024-05-13")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @ApiModelProperty(required = true, value = "Game platform")
    @NotNull(message = "Platform is required")
    @ValidEnum(name = "country", enumClass = Country.class)
    private Country country;

    @ApiModelProperty(required = true, value = "Game platform")
    @NotNull(message = "Platform is required")
    @EnumPattern(name = "platform", regexp = "PC|XBOX|XBOX_SERIES_X|PLAYSTATION|NINTENDO_SWITCH|MOBILE")
    private Platform platform;

    @ApiModelProperty(required = true, value = "Game status")
    @NotNull(message = "Game status is required")
    @ValidEnum(name = "status", enumClass = GameStatus.class)
    private GameStatus status;

    @ApiModelProperty(required = true, value = "Game developer name")
    @NotBlank(message = "Developer name is required")
    private String developer;

    @ApiModelProperty(required = true, value = "Game publisher name")
    @NotBlank(message = "Publisher name is required")
    private String publisher;

    @ApiModelProperty(value = "Game stock")
    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    @ApiModelProperty(value = "Game discount (percentage)")
    @Min(value = 0, message = "Discount cannot be negative")
    @Max(value = 100, message = "Discount cannot be greater than 100%")
    private BigDecimal discount;

    @ApiModelProperty(required = true, value = "Game age rating")
    @NotBlank(message = "Age rating is required")
    private String ageRating;

    @ApiModelProperty(required = true, value = "IDs of associated genres")
    private Set<Long> genres;

    @ApiModelProperty(required = true, value = "IDs of associated features")
    private Set<Long> features;

    @ApiModelProperty(value = "List of slide images to keep associated with the game")
    private List<SlideImageRequestDTO> slideImages;

}
