package com.deltegui.plantio.game.implementation;

import com.deltegui.plantio.game.application.GameRepository;
import com.deltegui.plantio.game.domain.Game;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class MemoryGameRepository implements GameRepository {
    private final HashMap<String, Game> games;

    public MemoryGameRepository() {
        this.games = new HashMap<>();
    }

    @Override
    public void save(Game game) {
        this.games.put(game.getOwner(), game);
    }

    @Override
    public Optional<Game> load(String userName) {
        var element = this.games.get(userName);
        if (element == null) return Optional.empty();
        return Optional.of(element);
    }
}
