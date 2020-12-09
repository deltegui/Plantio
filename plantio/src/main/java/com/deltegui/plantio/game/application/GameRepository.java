package com.deltegui.plantio.game.application;

import com.deltegui.plantio.game.domain.Game;

import java.util.Optional;

public interface GameRepository {
    void save(Game game);
    Optional<Game> load(String userName);
}
