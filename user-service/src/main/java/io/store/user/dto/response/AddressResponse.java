package io.store.user.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Address request DTO")
public class AddressResponse implements Serializable {

    @Schema(description = "ID of the address", example = "1")
    private Long id;

    @Schema(description = "Street address", example = "123 Main St")
    private String street;

    @Schema(description = "City", example = "Springfield")
    private String city;

    @Schema(description = "State", example = "IL")
    private String state;

    @Schema(description = "Country", example = "USA")
    private String country;

    @Schema(description = "Postal code", example = "62704")
    private String postalCode;

}