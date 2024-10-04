package io.store.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Address request DTO")
public class AddressRequest {

    @Schema(description = "Street address", example = "123 Main St")
    @NotEmpty(message = "Street is required")
    private String street;

    @Schema(description = "City", example = "Springfield")
    @NotEmpty(message = "City is required")
    private String city;

    @Schema(description = "State", example = "IL")
    @NotEmpty(message = "State is required")
    private String state;

    @Schema(description = "Country", example = "USA")
    @NotEmpty(message = "Country is required")
    private String country;

    @Schema(description = "Postal code", example = "62704")
    @NotEmpty(message = "Postal code is required")
    private String postalCode;


}
