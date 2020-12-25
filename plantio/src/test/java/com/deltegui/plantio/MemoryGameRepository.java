package com.deltegui.plantio;

import com.deltegui.plantio.game.application.GameRepository;
import com.deltegui.plantio.game.domain.Game;

import java.util.*;

public class MemoryGameRepository implements GameRepository {
    private final List<Game> games;

    private MemoryGameRepository(List<Game> games) {
        this.games = games;
    }

    private MemoryGameRepository() {
        this.games = new ArrayList<>();
    }

    public static MemoryGameRepository withGames(Game... games) {
        return new MemoryGameRepository(new ArrayList<>(Arrays.asList(games)));
    }

    @Override
    public void save(Game game) {
        this.games.add(game);
    }

    @Override
    public Optional<Game> load(String userName) {
        for (Game g : games) {
            if (g.getOwner().equals(userName)) {
                return Optional.of(g);
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Game game) {
        for (Game g : games) {
            if (g.equals(game)) {
                this.games.remove(g);
                this.games.add(game);
            }
        }
    }

    public int size() {
        return this.games.size();
    }

    public Game getElement(int i) {
        return this.games.get(i);
    }
}
