package com.deltegui.plantio.game.application;

import com.deltegui.plantio.game.domain.Plant;
import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.game.domain.Position;
import com.deltegui.plantio.game.domain.WateredState;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public final class PlantRequest {
    private @NotNull final PlantType type;
    private @NotNull @Min(0) @Max(6) final int phase;
    private @NotNull final Position position;
    private @NotNull final WateredState watered;
    private @NotNull @Min(0) @Max(100) final double humidity;

    public PlantRequest(PlantType type, int phase, Position position, WateredState watered, double humidity) {
        this.type = type;
        this.phase = phase;
        this.position = position;
        this.watered = watered;
        this.humidity = humidity;
    }

    Plant createNewPlant() {
        return new Plant(
                type,
                watered,
                phase,
                position,
                humidity,
                0,
                LocalDateTime.now()
        );
    }

    Plant merge(Plant other) {
        return new Plant(
                type,
                watered,
                phase,
                position,
                humidity,
                other.getHoursWet(),
                other.getLastAppliedReportDate()
        );
    }

    boolean comesFromPlant(Plant origin) {
        return origin.getPosition().equals(this.position);
    }
}
