package io.store.steam.repository;

import io.store.steam.dto.response.FeatureResponseDTO;
import io.store.steam.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Feature findByName(String name);
}
