package io.store.steam.utils;

import io.store.steam.dto.request.GameRequestDTO;
import io.store.steam.dto.response.GameResponseDTO;
import io.store.steam.model.Game;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameMapper {
    Game toEntity(GameRequestDTO gameRequestDTO);
    GameResponseDTO toDTO(Game game);
}
