package io.store.steam.utils;

import io.store.steam.dto.request.GameRequestDTO;
import io.store.steam.dto.response.GameResponseDTO;
import io.store.steam.model.Game;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMapperImpl implements GameMapper {

    private final ModelMapper modelMapper;

    @Override
    public Game toEntity(GameRequestDTO gameRequestDTO) {
        return modelMapper.map(gameRequestDTO, Game.class);
    }

    @Override
    public GameResponseDTO toDTO(Game game) {
        return modelMapper.map(game, GameResponseDTO.class);
    }
}
