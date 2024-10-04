package io.store.user.dto.response;

import io.store.user.model.enums.Gender;
import io.store.user.model.enums.UserStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User response DTO")
public class UserResponse {
    @Schema(description = "ID of the user", example = "1")
    private Long id;

    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Schema(description = "Email of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Phone number of the user", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Date of birth of the user", example = "1990-01-01")
    private Date dateOfBirth;

    @Schema(description = "Gender of the user", example = "MALE")
    private Gender gender;

    @Schema(description = "Status of the user", example = "ACTIVE")
    private UserStatus status;

    @Schema(description = "Roles assigned to the user")
    private Set<RoleResponse> roles;

    @Schema(description = "Groups assigned to the user")
    private Set<GroupResponse> groups;

    @Schema(description = "Addresses of the user")
    private Set<AddressResponse> addresses;

    @Schema(description = "Creation date of the user", example = "2023-06-01")
    private LocalDateTime createdAt;

    @Schema(description = "Last update date of the user", example = "2023-06-10")
    private LocalDateTime updatedAt;
}
