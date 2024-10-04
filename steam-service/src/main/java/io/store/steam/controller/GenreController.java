package io.store.steam.controller;

import io.store.steam.dto.request.GenreRequestDTO;
import io.store.steam.model.Genre;
import io.store.steam.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "API for Genre description", description = "Method for Genre")
@RequestMapping("/api/genre")
@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @Operation(summary = "Get all genres", tags = { "genre", "get" })
    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreService.findAllGenres();
        return ResponseEntity.ok(genres);
    }

    @Operation(summary = "Get genre by ID", tags = { "genre", "get" })
    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        Optional<Genre> genre = genreService.findGenreById(id);
        return genre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new genre", tags = { "genre", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Genre created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody GenreRequestDTO genreRequestDTO) {
        Genre savedGenre = genreService.saveGenre(genreRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
    }

    @Operation(summary = "Create multiple genres", tags = { "genre", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Genres created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/batch")
    public ResponseEntity<List<Genre>> createGenres(@RequestBody List<String> genreNames) {
        List<Genre> createdGenres = genreService.createGenres(genreNames);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenres);
    }

    @Operation(summary = "Delete genre by ID", tags = { "genre", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Genre deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenreById(id);
        return ResponseEntity.noContent().build();
    }

}
