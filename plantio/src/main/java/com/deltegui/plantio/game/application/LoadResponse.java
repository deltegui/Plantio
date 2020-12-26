package com.deltegui.plantio.game.application;

import com.deltegui.plantio.game.domain.Game;
import com.deltegui.plantio.game.domain.GameEvent;

import java.util.List;

public final class LoadResponse {
    private final Game game;
    private final List<GameEvent> events;

    public LoadResponse(Game game, List<GameEvent> events) {
        this.game = game;
        this.events = events;
    }

    public Game getGame() {
        return game;
    }

    public List<GameEvent> getEvents() {
        return events;
    }
}
