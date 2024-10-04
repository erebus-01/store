package io.store.steam.controller;

import io.store.steam.dto.request.GameRequestDTO;
import io.store.steam.dto.response.GameResponseDTO;
import io.store.steam.utils.response.PageableResponse;
import io.store.steam.service.GameService;
import io.store.steam.utils.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Tag(name = "API for game description", description = "Method for game include GET, PUT, UPDATE, DELETE")
@RequestMapping("/api/game")
@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final JobLauncher jobLauncher;
    private final Job job;

    @Operation(summary = "Create a new Game", tags = { "games", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = GameResponseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    public ResponseData<GameResponseDTO> createGame(@RequestBody GameRequestDTO gameCreateRequestDTO) throws Exception {
        GameResponseDTO gameResponseDTO = gameService.createGame(gameCreateRequestDTO);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Created new a game", gameResponseDTO);
    }

    @Operation(summary = "Import new Games from csv file", tags = { "games", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = GameResponseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/import-csv")
    public void importCsvToDBJob() {
        JobParameters jobParameter = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(job, jobParameter);
        } catch (JobExecutionAlreadyRunningException
                 | JobRestartException
                 | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Get Game by ID", tags = { "games", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = GameResponseDTO.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/{gameId}")
    public ResponseData<GameResponseDTO> getGameById(@PathVariable UUID gameId) throws Exception {
        GameResponseDTO gameResponseDTO = gameService.getGameById(gameId);
        return new ResponseData<>(HttpStatus.OK.value(), "Game", gameResponseDTO);
    }

    @Operation(summary = "Update a Game", tags = { "games", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = GameResponseDTO.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }), // Bad request
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }), // Not found
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @PutMapping("/{gameId}")
    public ResponseData<?> updateGame(@PathVariable UUID gameId, @RequestBody GameRequestDTO gameUpdateRequestDTO) throws Exception {
        gameService.updateGame(gameId, gameUpdateRequestDTO);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Updated a game successfully.");
    }

    @Operation(summary = "Delete a Game", tags = { "games", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content on successful deletion"), // No content on successful deletion
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }), // Not found
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("/{gameId}")
    public ResponseData<Void> deleteGame(@PathVariable UUID gameId) throws Exception {
        gameService.deleteGame(gameId);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete a game successfully");
    }

    @Operation(summary = "Get Games (List)", tags = { "games", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = PageableResponse.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) // Bad request on invalid parameters
    })
    @GetMapping
    public ResponseEntity<PageableResponse<GameResponseDTO>> getGames(
            @RequestParam(required = false) Map<String, String> filterParams,
            Pageable pagination,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) throws Exception {
        PageableResponse<GameResponseDTO> gamesResponse = gameService.getGames(filterParams, pagination, sortField, sortDirection);
        return ResponseEntity.ok(gamesResponse);
    }

    @Operation(summary = "Get Games (List)", tags = { "games", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = PageableResponse.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/search")
    public ResponseEntity<PageableResponse<GameResponseDTO>> searchGames(
            @RequestParam(required = false) String[] params,
            Pageable pageable) {
        PageableResponse<GameResponseDTO> response = gameService.searchGames(params, pageable);
        return ResponseEntity.ok(response);
    }

}
