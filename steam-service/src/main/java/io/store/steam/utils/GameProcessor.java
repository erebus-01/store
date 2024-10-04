package io.store.steam.utils;

import io.store.steam.model.Game;
import org.springframework.batch.item.ItemProcessor;

public class GameProcessor implements ItemProcessor<Game, Game> {

    @Override
    public Game process(Game item) throws Exception {
        return item;
    }
}
