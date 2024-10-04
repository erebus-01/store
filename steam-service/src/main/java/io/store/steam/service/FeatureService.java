package io.store.steam.service;

import io.store.steam.dto.request.FeatureRequestDTO;
import io.store.steam.utils.response.PageableResponse;
import io.store.steam.model.Feature;

import java.util.List;
import java.util.Optional;

public interface FeatureService {
    PageableResponse<?> findAllFeatures();

    Optional<Feature> findFeatureById(Long id);

    Feature saveFeature(FeatureRequestDTO feature);
    List<Feature> createFeatures(List<String> featureNames);

    void deleteFeatureById(Long id);
}
