package io.store.steam.service;

import io.store.steam.dto.request.GenreRequestDTO;
import io.store.steam.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> findAllGenres();

    Optional<Genre> findGenreById(Long id);

    Genre saveGenre(GenreRequestDTO feature);
    List<Genre> createGenres(List<String> genreNames);

    void deleteGenreById(Long id);
}
