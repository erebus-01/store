package io.store.steam.model;

import io.store.steam.model.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Title is required")
    @Column(name = "title", unique = true)
    private String title;

    @Lob
    @NotBlank(message = "Description is required")
    @Column(name = "description")
    private String description;

    @NotBlank(message = "ImageUrl is required")
    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "price")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be positive")
    private BigDecimal price;

    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date releaseDate;

    @Column(name = "developer")
    @NotBlank(message = "Developer name is required")
    private String developer;

    @Column(name = "publisher")
    @NotBlank(message = "Publisher name is required")
    private String publisher;

    @Column(name = "platform")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Platform platform;

    @Column(name = "country")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Country country;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private GameStatus status;

    @Column(name = "stock")
    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    @Column(name = "discount")
    @Min(value = 0, message = "Discount cannot be negative")
    @Max(value = 100, message = "Discount cannot be greater than 100%")
    private BigDecimal discount;

    @Column(name = "age_rating")
    @NotBlank(message = "Age rating is required")
    private String ageRating;

    @ManyToMany
    @JoinTable(name = "game_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @ManyToMany
    @JoinTable(name = "game_feature",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<Feature> features;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<SlideImage> slideImages;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDate createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDate updateAt;
}
