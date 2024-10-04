package io.store.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "addresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street", nullable = false)
    @NotEmpty(message = "Street is required")
    private String street;

    @Column(name = "city", nullable = false)
    @NotEmpty(message = "City is required")
    private String city;

    @NotEmpty(message = "State is required")
    @Column(name = "state", nullable = false)
    private String state;

    @NotEmpty(message = "Postal code is required")
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotEmpty(message = "Country is required")
    @Column(name = "country", nullable = false)
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}