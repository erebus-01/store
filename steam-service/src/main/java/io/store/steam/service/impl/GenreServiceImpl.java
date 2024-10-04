package io.store.steam.service.impl;

import io.store.steam.dto.request.GenreRequestDTO;
import io.store.steam.model.Genre;
import io.store.steam.repository.GenreRepository;
import io.store.steam.service.GenreService;
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
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findGenreById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Genre saveGenre(GenreRequestDTO genreRequestDTO) {
        Genre genre = new Genre();
        genre.setName(genreRequestDTO.getName());
        return genreRepository.save(genre);
    }

    @Override
    public List<Genre> createGenres(List<String> genreNames) {
        List<Genre> genres = new ArrayList<>();

        for (String genreName : genreNames) {
            Genre genre = new Genre();
            genre.setName(genreName);
            genres.add(genre);
        }
        genreRepository.saveAll(genres);
        return genres;
    }

    @Override
    public void deleteGenreById(Long id) {
        genreRepository.deleteById(id);
    }
}
