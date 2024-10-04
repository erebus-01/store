package io.store.steam.service.impl;

import io.store.steam.dto.request.FeatureRequestDTO;
import io.store.steam.utils.response.PageableResponse;
import io.store.steam.model.Feature;
import io.store.steam.repository.FeatureRepository;
import io.store.steam.service.FeatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;

    @Override
    public PageableResponse<?> findAllFeatures() {
        return null;
    }

    @Override
    public Optional<Feature> findFeatureById(Long id) {
        return Optional.empty();
    }

    @Override
    public Feature saveFeature(FeatureRequestDTO featureRequest) {
        Feature feature = new Feature();
        feature.setName(featureRequest.getName());
        return featureRepository.save(feature);
    }

    @Override
    public List<Feature> createFeatures(List<String> featureNames) {
        List<Feature> features = new ArrayList<>();

        for (String featureName : featureNames) {
            Feature feature = new Feature();
            feature.setName(featureName);
            features.add(feature);
        }
        featureRepository.saveAll(features);
        return features;
    }

    @Override
    public void deleteFeatureById(Long id) {
        featureRepository.deleteById(id);
    }
}
