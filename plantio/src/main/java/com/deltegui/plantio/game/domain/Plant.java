package com.deltegui.plantio.game.domain;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Plant {
    public static class Position {
        private final int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
    @NotNull
    private final PlantType type;
    @NotNull @Min(0) @Max(6)
    private final int phase;
    @NotNull
    private final Position position;
    @NotNull
    private final WateredState watered;

    public Plant(PlantType type, WateredState watered, int phase, Plant.Position position) {
        this.type = type;
        this.phase = phase;
        this.position = position;
        this.watered = watered;
    }

    public PlantType getType() {
        return type;
    }

    public int getPhase() {
        return phase;
    }

    public Position getPosition() {
        return position;
    }

    public WateredState getWatered() {
        return watered;
    }
}
