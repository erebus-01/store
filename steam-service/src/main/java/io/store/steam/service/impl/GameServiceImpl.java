package io.store.steam.service.impl;

import io.store.steam.dto.request.GameRequestDTO;
import io.store.steam.dto.response.GameResponseDTO;
import io.store.steam.utils.response.PageableResponse;
import io.store.steam.exception.NotFoundException;
import io.store.steam.model.Feature;
import io.store.steam.model.Game;
import io.store.steam.model.Genre;
import io.store.steam.model.SlideImage;
import io.store.steam.repository.FeatureRepository;
import io.store.steam.repository.GameRepository;
import io.store.steam.repository.GenreRepository;
import io.store.steam.repository.SlideImageRepository;
import io.store.steam.repository.specification.GameSpecificationBuilder;
import io.store.steam.service.GameService;
import io.store.steam.utils.GameMapper;
import io.store.steam.utils.ValidateKeySearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final FeatureRepository featureRepository;
    private final SlideImageRepository slideImageRepository;
    private final GameMapper gameMapper;

    @Override
    @Transactional
    public GameResponseDTO createGame(GameRequestDTO gameCreateRequestDTO) throws Exception {
        Game game = gameMapper.toEntity(gameCreateRequestDTO);
        Set<Genre> genres = new HashSet<>();
        for (Long genreId : gameCreateRequestDTO.getGenres()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new NotFoundException("Genre with ID " + genreId + " not found"));
            genres.add(genre);
        }
        game.setGenres(genres);

        // Fetch features based on IDs
        Set<Feature> features = new HashSet<>();
        for (Long featureId : gameCreateRequestDTO.getFeatures()) {
            Feature feature = featureRepository.findById(featureId)
                    .orElseThrow(() -> new NotFoundException("Feature with ID " + featureId + " not found"));
            features.add(feature);
        }
        game.setFeatures(features);

        // Create and save new slide images
        if (gameCreateRequestDTO.getSlideImages() != null && !gameCreateRequestDTO.getSlideImages().isEmpty()) {
            Game finalGame = game;
            log.info("SlideImages: {}", gameCreateRequestDTO.getSlideImages());
            List<SlideImage> slideImages = gameCreateRequestDTO.getSlideImages().stream()
                    .map(slideImageRequestDTO -> {
                        SlideImage slideImage = new SlideImage();
                        slideImage.setImageUrl(slideImageRequestDTO.getImageUrl());
                        slideImage.setCaption(slideImageRequestDTO.getCaption());
                        slideImage.setDisplayOrder(slideImageRequestDTO.getDisplayOrder());
                        slideImage.setGame(finalGame); // Associate slide image with the game
                        return slideImage;
                    })
                    .collect(Collectors.toList());
            game.setSlideImages(slideImages); // Set slide images for the game
        }
        game = gameRepository.save(game);
        return gameMapper.toDTO(game);
    }

    @Override
    @Transactional(readOnly = true)
    public GameResponseDTO getGameById(UUID gameId) throws Exception {
        Optional<Game> gameOptional = gameRepository.findById(gameId);

        if (gameOptional.isEmpty()) {
            throw new NotFoundException("Game not found with id: " + gameId);
        }

        return gameMapper.toDTO(gameOptional.get());
    }

    @Override
    @Transactional
    public GameResponseDTO updateGame(UUID gameId, GameRequestDTO gameUpdateRequestDTO) throws Exception {
        Optional<Game> gameOptional = gameRepository.findById(gameId);

        if (gameOptional.isEmpty()) {
            throw new NotFoundException("Game not found with id: " + gameId);
        }

        Game game = gameOptional.get();

        // Update game entity with new values from DTO (null-safe approach)
        game.setTitle(Optional.ofNullable(gameUpdateRequestDTO.getTitle()).orElse(game.getTitle()));
        game.setDescription(Optional.ofNullable(gameUpdateRequestDTO.getDescription()).orElse(game.getDescription()));
        game.setImageUrl(Optional.ofNullable(gameUpdateRequestDTO.getImageUrl()).orElse(game.getImageUrl()));
        game.setPrice(Optional.ofNullable(gameUpdateRequestDTO.getPrice()).orElse(game.getPrice()));
        game.setReleaseDate(Optional.ofNullable(gameUpdateRequestDTO.getReleaseDate()).orElse(game.getReleaseDate()));
        game.setDeveloper(Optional.ofNullable(gameUpdateRequestDTO.getDeveloper()).orElse(game.getDeveloper()));
        game.setPublisher(Optional.ofNullable(gameUpdateRequestDTO.getPublisher()).orElse(game.getPublisher()));
        game.setPlatform(Optional.ofNullable(gameUpdateRequestDTO.getPlatform()).orElse(game.getPlatform()));
        game.setCountry(Optional.ofNullable(gameUpdateRequestDTO.getCountry()).orElse(game.getCountry()));
        game.setStatus(Optional.ofNullable(gameUpdateRequestDTO.getStatus()).orElse(game.getStatus()));
        game.setStock(Optional.of(gameUpdateRequestDTO.getStock()).orElse(game.getStock()));
        game.setDiscount(Optional.ofNullable(gameUpdateRequestDTO.getDiscount()).orElse(game.getDiscount()));
        game.setAgeRating(Optional.ofNullable(gameUpdateRequestDTO.getAgeRating()).orElse(game.getAgeRating()));

        if (gameUpdateRequestDTO.getGenres() != null && !gameUpdateRequestDTO.getGenres().isEmpty()) {
            Set<Genre> genres = gameUpdateRequestDTO.getGenres().stream()
                    .map(genreId -> {
                        try {
                            return genreRepository.findById(genreId)
                                    .orElseThrow(() -> new Exception("Genre not found with ID: " + genreId));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toSet());
            game.setGenres(genres);
        } else {
            game.getGenres().clear();
        }

        if (gameUpdateRequestDTO.getFeatures() != null && !gameUpdateRequestDTO.getFeatures().isEmpty()) {
            Set<Feature> features = gameUpdateRequestDTO.getFeatures().stream()
                    .map(featureId -> {
                        try {
                            return featureRepository.findById(featureId)
                                    .orElseThrow(() -> new Exception("Feature not found with ID: " + featureId));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toSet());
            game.setFeatures(features);
        } else {
            game.getFeatures().clear();
        }

        game = gameRepository.save(game);

        return gameMapper.toDTO(game);
    }

    @Override
    @Transactional
    public void deleteGame(UUID gameId) throws Exception {
        Game gameToDelete = gameRepository.findById(gameId).orElseThrow(() -> new Exception("Game not found with ID: " + gameId));
        gameRepository.delete(gameToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public PageableResponse<GameResponseDTO> getGames(Map<String, String> filterParams, Pageable pagination, String sortField, String sortDirection) throws DataAccessException  {
        Specification<Game> filterSpec = buildFilterSpec(filterParams);
        Pageable pageableWithSort = buildPageableWithSort(pagination, sortField, sortDirection);

        Page<Game> gamePage = (filterSpec != null)
                ? gameRepository.findAll(filterSpec, pageableWithSort)
                : gameRepository.findAll(pageableWithSort);

        List<GameResponseDTO> gamesDto = gamePage.getContent().stream()
                .map(gameMapper::toDTO)
                .collect(Collectors.toList());

        return PageableResponse.<GameResponseDTO>builder()
                .page(gamePage.getNumber())
                .size(gamePage.getSize())
                .total(gamePage.getTotalElements())
                .items(gamesDto)
                .build();
    }

    @Override
    public PageableResponse<GameResponseDTO> searchGames(String[] params, Pageable pageable) {
        GameSpecificationBuilder builder = new GameSpecificationBuilder();
        log.info("params is {}", (Object) params);

        for (String param : params) {
            Pattern pattern = Pattern.compile("(\\w+?)([<:>~!])(.*)(\\p{Punct}?)(\\p{Punct}?)");
            Matcher matcher = pattern.matcher(param);
            if (matcher.find()) {
                builder.with(ValidateKeySearch.validateKeyForSearch(matcher.group(1)), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
            }
        }

        Page<Game> games = gameRepository.findAll(builder.build(), pageable);

        List<GameResponseDTO> gameResponseDTOs = games.getContent().stream()
                .map(gameMapper::toDTO)
                .collect(Collectors.toList());

        return PageableResponse.<GameResponseDTO>builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(gameResponseDTOs.size())
                .items(gameResponseDTOs)
                .sort(pageable.getSort())
                .build();
    }

    private Specification<Game> buildFilterSpec(Map<String, String> filterParams) {

//        GameSpecificationBuilder builder = new GameSpecificationBuilder();
//
//        Pattern pattern = Pattern.compile("(\\w+?)([<:>~!])(.*)(\\p{Punct}?)(\\p{Punct}?)"); // Handles quoted values
//
//        Matcher matcher = pattern.matcher(filterParams + ",");
//        while (matcher.find()) {
//            String key = ValidateKeySearch.validateKeyForSearch(matcher.group(1)); // Validate key
//            String operation = matcher.group(2);
//            String value = matcher.group(3); // Handle quoted values
//
//            // Handle potential null values from matcher groups
//            if (key != null && operation != null && value != null) {
//                builder.with(key, operation, value, matcher.group(4), matcher.group(5));
//            }
//        }
//
//        return builder.build();

        Specification<Game> spec = Specification.where(null);
        for (Map.Entry<String, String> entry : filterParams.entrySet()) {
            String filterKey = entry.getKey();
            String filterValue = entry.getValue();

            spec = spec.and((root, query, criteriaBuilder) -> {
                if (filterKey.equals("title")) {
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + filterValue.toLowerCase() + "%");
                } else if (filterKey.equals("developer")) {
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("developer")), "%" + filterValue.toLowerCase() + "%");
                }
                return null;
            });
        }
        return spec;
    }


    private Pageable buildPageableWithSort(Pageable pagination, String sortField, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Set<String> allowedSortFields = Set.of("id", "title", "price", "genres", "platform", "status", "createdAt", "stock");
        if (sortField != null && sortDirection != null && allowedSortFields.contains(sortField)) {
            Sort.Direction direction = Sort.Direction.fromString(sortDirection);
            sort = Sort.by(direction, sortField);
        }

        return PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), sort);
    }

}
