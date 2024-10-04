package io.store.user.dto.request;

import io.store.user.model.enums.Gender;
import io.store.user.model.enums.UserStatus;
import io.store.user.utils.validator.enums.EnumPattern;
import io.store.user.utils.validator.enums.ValidEnum;
import io.store.user.utils.validator.phone.PhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User request DTO")
public class UserRequest {

    @Schema(name = "username", description = "Username of the user", example = "john_doe", required = true)
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @Schema(name = "password", description = "Password of the user", example = "password1234", required = true)
    @NotBlank(message = "Password is required")
    private String password;

    @Schema(name = "firstname", description = "First name of the user", example = "John", required = true)
    @NotBlank(message = "Firstname is required")
    @Size(min = 1, max = 20, message = "First name must be between 1 and 20 characters")
    private String firstName;

    @Schema(name = "lastname", description = "Last name of the user", example = "Doe", required = true)
    @NotBlank(message = "Lastname is required")
    @Size(min = 1, max = 20, message = "Last name must be between 1 and 20 characters")
    private String lastName;

    @Schema(name = "email", description = "Email of the user", example = "john.doe@example.com", required = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(name = "phone number", description = "Phone number of the user", example = "+1234567890", required = true)
    @NotBlank(message = "Phone number is required")
    @PhoneNumber
    private String phoneNumber;

    @Schema(name = "date of birth", description = "Date of birth of the user", example = "1990-01-01", required = true)
    @NotEmpty(message = "Date of birth is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfBirth;

    @Schema(name = "gender", description = "Gender of the user", example = "MALE", required = true)
    @EnumPattern(name = "gender", regexp = "MALE|FEMALE|OTHER")
    private Gender gender;

    @Schema(name = "status", description = "Status of the user", example = "ACTIVE", required = true)
    @ValidEnum(name = "status", enumClass = UserStatus.class)
    private UserStatus status;

    @Schema(description = "Role IDs assigned to the user")
    private Set<Long> roleIds;

    @Schema(description = "Group IDs assigned to the user")
    private Set<Long> groupIds;

    @Schema(description = "Addresses of the user")
    private Set<AddressRequest> addresses;
}
