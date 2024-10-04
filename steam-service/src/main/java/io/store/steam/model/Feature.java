package io.store.steam.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Feature name is required")
    private String name;

    @ManyToMany(mappedBy = "features")
    private Set<Game> games;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDate createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDate updateAt;
}
