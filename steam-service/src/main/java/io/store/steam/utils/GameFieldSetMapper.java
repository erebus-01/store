package io.store.steam.utils;

import io.store.steam.model.Feature;
import io.store.steam.model.Game;
import io.store.steam.model.Genre;
import io.store.steam.model.SlideImage;
import io.store.steam.model.enums.Country;
import io.store.steam.model.enums.GameStatus;
import io.store.steam.model.enums.Platform;
import io.store.steam.repository.FeatureRepository;
import io.store.steam.repository.GenreRepository;
import io.store.steam.repository.SlideImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GameFieldSetMapper implements FieldSetMapper<Game> {

    private final FeatureRepository featureRepository;
    private final GenreRepository genreRepository;
    private final SlideImageRepository slideImageRepository;


    @Override
    public Game mapFieldSet(FieldSet fieldSet) throws BindException {
        Game game = new Game();
        game.setTitle(fieldSet.readString("title"));
        game.setDescription(fieldSet.readString("description"));
        game.setImageUrl(fieldSet.readString("imageUrl"));
        game.setPrice(fieldSet.readBigDecimal("price"));
        game.setDeveloper(fieldSet.readString("developer"));
        game.setPublisher(fieldSet.readString("publisher"));
        game.setReleaseDate(fieldSet.readDate("releaseDate"));
        game.setPlatform(Platform.valueOf(fieldSet.readString("platform")));
        game.setCountry(Country.valueOf(fieldSet.readString("country")));
        game.setStatus(GameStatus.valueOf(fieldSet.readString("status")));
        game.setStock(fieldSet.readInt("stock"));
        game.setDiscount(fieldSet.readBigDecimal("discount"));
        game.setAgeRating(fieldSet.readString("ageRating"));

        // Handle Features
        String[] featureNames = fieldSet.readString("features").split(",");
        Set<Feature> features = new HashSet<>();
        for (String featureName : featureNames) {
            features.add(featureRepository.findByName(featureName.trim()));
        }
        game.setFeatures(features);

        // Handle Genres
        String[] genreNames = fieldSet.readString("genres").split(",");
        Set<Genre> genres = new HashSet<>();
        for (String genreName : genreNames) {
            genres.add(genreRepository.findByName(genreName.trim()));
        }
        game.setGenres(genres);

        // Handle SlideImages
        String[] slideImagesData = fieldSet.readString("slideImages").split(";");
        List<SlideImage> slideImages = Arrays.stream(slideImagesData)
                .map(slideImageData -> {
                    String[] parts = slideImageData.split(",");
                    if (parts.length == 3) {
                        SlideImage slideImage = new SlideImage();
                        slideImage.setImageUrl(parts[0].trim());
                        slideImage.setCaption(parts[1].trim());
                        slideImage.setDisplayOrder(Integer.parseInt(parts[2].trim()));
                        slideImage.setGame(game); // Associate slide image with the game
                        return slideImage;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        game.setSlideImages(slideImages);

        return game;    }
}
