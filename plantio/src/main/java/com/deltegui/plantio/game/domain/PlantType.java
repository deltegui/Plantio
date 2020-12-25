package com.deltegui.plantio.game.domain;

import com.deltegui.plantio.weather.domain.TemperatureInterval;
import com.deltegui.plantio.weather.domain.WeatherReport;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;

public enum PlantType {
    WHEAT(TemperatureInterval.withMinMax(-10, 28), 20, 30, Duration.ofDays(1), 0.05),
    CACTUS(TemperatureInterval.withMinMax(25, 50), 10, 23, Duration.ofDays(2), 0.01);
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
            default: return PlantType.WHEAT;
        }
    }
}
