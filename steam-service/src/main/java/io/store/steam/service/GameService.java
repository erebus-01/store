package io.store.steam.service;

import io.store.steam.dto.request.GameRequestDTO;
import io.store.steam.dto.response.GameResponseDTO;
import io.store.steam.utils.response.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface GameService {

    GameResponseDTO createGame(GameRequestDTO gameCreateRequestDTO) throws Exception;
    GameResponseDTO getGameById(UUID gameId) throws Exception;
    GameResponseDTO updateGame(UUID gameId, GameRequestDTO gameUpdateRequestDTO) throws Exception;
    void deleteGame(UUID gameId) throws Exception;
    PageableResponse<GameResponseDTO> getGames(Map<String, String> filterParams, Pageable pagination, String sortField, String sortDirection) throws Exception;

    PageableResponse<GameResponseDTO> searchGames(String[] params, Pageable pageable);

}
