package com.deltegui.plantio.game.domain;

import com.deltegui.plantio.weather.domain.TemperatureInterval;
import com.deltegui.plantio.weather.domain.WeatherReport;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Optional;

public enum PlantType {
    WHEAT(TemperatureInterval.withMinMax(16, 30), 20, 30, Duration.ofHours(9), 0.07),
    LAVENDER(TemperatureInterval.withMinMax(-10, 17), 40, 55, Duration.ofHours(13), 0.25),
    CACTUS(TemperatureInterval.withMinMax(25, 50), 10, 23, Duration.ofHours(15), 0.05);
    private @NotNull final TemperatureInterval temperatureInterval;
    private @NotNull @Min(0) @Max(100) final int humidityDeath;
    private @NotNull @Min(0) @Max(100) final int humidityToGrowth;
    private @NotNull final Duration growthDuration;
    private @NotNull final double humidityGrowthFactor;

    PlantType(
            TemperatureInterval temperatureInterval,
            int humidityDeath,
            int humidityToGrowth,
            Duration growthDuration,
            double humidityGrowthFactor
    ) {
        this.temperatureInterval = temperatureInterval;
        this.humidityDeath = humidityDeath;
        this.humidityToGrowth = humidityToGrowth;
        this.growthDuration = growthDuration;
        this.humidityGrowthFactor = humidityGrowthFactor;
    }

    public boolean shouldBeDead(WeatherReport report, Plant plant) {
        return (! report.isInInterval(this.temperatureInterval)) || plant.getHumidity() <= this.humidityDeath;
    }

    public Optional<GameEventType> getDeadReason(WeatherReport report, Plant plant) {
        if (this.temperatureInterval.isLower(report.getTemperature())) {
            return Optional.of(GameEventType.KILLED_TEMPERATURE_LOW);
        }
        if (this.temperatureInterval.isUpper(report.getTemperature())) {
            return Optional.of(GameEventType.KILLED_TEMPERATURE_HIGH);
        }
        if (plant.getHumidity() <= this.humidityDeath) {
            return Optional.of(GameEventType.KILLED_DRY);
        }
        return Optional.empty();
    }

    public boolean shouldGrowth(Plant plant) {
        return plant.getHumidity() >= this.humidityToGrowth &&
                plant.getHoursWet() >= growthDuration.toHours();
    }

    public void growthPlant(Plant plant) {
        long phases = plant.getHoursWet() / growthDuration.toHours();
        plant.growth((int)phases);
    }

    public boolean shouldBeWet(Plant plant) {
        return plant.getHumidity() >= this.humidityToGrowth;
    }

    public double getHumidityGrowthFactor() {
        return humidityGrowthFactor;
    }

    public static PlantType fromString(String type) {
        switch (type) {
            case "CACTUS": return PlantType.CACTUS;
            case "LAVENDER": return PlantType.LAVENDER;
            default: return PlantType.WHEAT;
        }
    }
}
