package com.deltegui.plantio.game.domain;

public final class GameEvent {
    private final GameEventType eventType;
    private final Position position;

    public GameEvent(GameEventType eventType, Position plantPosition) {
        this.eventType = eventType;
        this.position = plantPosition;
    }

    public boolean isEventType(GameEventType eventType) {
        return eventType == this.eventType;
    }

    public Position getPosition() {
        return this.position;
    }

    public GameEventType getEventType() {
        return eventType;
    }
}
