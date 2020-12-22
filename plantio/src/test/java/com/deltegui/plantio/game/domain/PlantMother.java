package com.deltegui.plantio.game.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PlantMother {
    public static Plant createPlant(double initialHumidity, PlantType type) {
        return new Plant(
                type,
                WateredState.DRY,
                0,
                new Position(0, 0),
                initialHumidity,
                0,
                LocalDateTime.now()
        );
    }

    public static Plant createPlant(PlantType type, int phase) {
        return new Plant(
                type,
                WateredState.DRY,
                phase,
                new Position(0, 0),
                0,
                0,
                LocalDateTime.now()
        );
    }

    public static Plant createPlant(double initialHumidity, PlantType type, long hoursElapsed) {
        return new Plant(
                type,
                WateredState.DRY,
                0,
                new Position(0, 0),
                initialHumidity,
                0,
                LocalDateTime.now().minus(hoursElapsed, ChronoUnit.HOURS)
        );
    }

    public static Plant createPlant(double initialHumidity, PlantType type, long hoursElapsed, WateredState state) {
        return new Plant(
                type,
                state,
                0,
                new Position(0, 0),
                initialHumidity,
                0,
                LocalDateTime.now().minus(hoursElapsed, ChronoUnit.HOURS)
        );
    }

    public static Plant createPlant(double initialHumidity, PlantType type, long hoursElapsed, WateredState state, int phase) {
        return new Plant(
                type,
                state,
                phase,
                new Position(0, 0),
                initialHumidity,
                0,
                LocalDateTime.now().minus(hoursElapsed, ChronoUnit.HOURS)
        );
    }

    public static Plant deadPlant() {
        return new Plant(
                PlantType.WHEAT,
                WateredState.DRY,
                Plant.DEATH_PHASE,
                new Position(0, 0),
                0,
                0,
                LocalDateTime.now()
        );
    }
}
