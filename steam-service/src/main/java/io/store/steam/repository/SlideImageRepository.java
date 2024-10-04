package io.store.steam.repository;

import io.store.steam.model.SlideImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideImageRepository extends JpaRepository<SlideImage, Long> {
}
