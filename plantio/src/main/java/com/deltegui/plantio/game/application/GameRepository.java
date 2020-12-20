package com.deltegui.plantio.game.application;

import com.deltegui.plantio.game.domain.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    void save(Game game);
    Optional<Game> load(String userName);
    void update(Game game);
    List<Game> getAllWithoutPlants();
}
