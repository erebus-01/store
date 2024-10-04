package io.store.steam.controller;

import io.store.steam.dto.request.FeatureRequestDTO;
import io.store.steam.dto.request.GameRequestDTO;
import io.store.steam.dto.response.GameResponseDTO;
import io.store.steam.model.Feature;
import io.store.steam.service.FeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "API for feature description", description = "Method for feature")
@RequestMapping("/api/feature")
@RestController
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;

    @Operation(summary = "Create a new feature", tags = { "feature", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = GameResponseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    public ResponseEntity<Feature> createGame(@RequestBody FeatureRequestDTO featureRequestDTO) throws Exception {
        Feature featureResponseDTO = featureService.saveFeature(featureRequestDTO);
        return ResponseEntity.ok(featureResponseDTO);
    }

    @Operation(summary = "Create new features", tags = { "feature", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Feature.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/list-features")
    public ResponseEntity<List<Feature>> createFeatures(@RequestBody List<String> featureNames) {
        List<Feature> createdFeatures = featureService.createFeatures(featureNames);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeatures);
    }

}
