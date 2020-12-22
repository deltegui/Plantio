package com.deltegui.plantio.game.domain;

import com.deltegui.plantio.weather.domain.WeatherReport;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class Plant {
    public static final int DEATH_PHASE = 6;
    public static final int END_PHASE = 5;

    private @NotNull final PlantType type;
    private @NotNull @Min(0) @Max(6) int phase;
    private @NotNull final Position position;
    private @NotNull WateredState watered;
    private @NotNull @Min(0) @Max(100) double humidity;
    private int hoursWet;
    private LocalDateTime lastAppliedReportDate;

    public Plant(
            PlantType type,
            WateredState watered,
            int phase,
            Position position,
            double humidity,
            int hoursWet,
            LocalDateTime lastAppliedReportDate
    ) {
        this.type = type;
        this.phase = phase;
        this.position = position;
        this.watered = watered;
        this.humidity = humidity;
        this.hoursWet = hoursWet;
        this.lastAppliedReportDate = lastAppliedReportDate;
    }

    public void applyWeather(WeatherReport report) {
        if (this.phase == DEATH_PHASE) {
            return;
        }
        this.humidity = report.calculateHumidity(this);
        this.setWateredSateUsing(report);
        this.lastAppliedReportDate = report.getCreation();
        if (this.type.shouldBeDead(report, this)) {
            this.kill();
            return;
        }
        if (this.type.shouldGrowth(this)) {
            this.type.growthPlant(this);
        }
    }

    public void kill() {
        this.watered = WateredState.DRY;
        this.phase = DEATH_PHASE;
    }

    public void growth(int phases) {
        this.watered = WateredState.WET;
        var futurePhase = this.phase + phases;
        if(futurePhase >= END_PHASE) {
            this.phase = END_PHASE;
            return;
        }
        this.phase = futurePhase;
    }

    private void setWateredSateUsing(WeatherReport report) {
        if (this.watered == WateredState.WET && this.type.shouldBeWet(this)) {
            this.addTimeWet(report);
        } else if (this.type.shouldBeWet(this)) {
            this.watered = WateredState.WET;
        } else {
            this.watered = WateredState.DRY;
        }
    }

    private void addTimeWet(WeatherReport report) {
        this.hoursWet += report.calculateHoursFrom(this.lastAppliedReportDate);
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

    public double getHumidity() {
        return humidity;
    }

    public double getHumidityGrowthFactor() {
        return this.type.getHumidityGrowthFactor();
    }

    public int getHoursWet() {
        return hoursWet;
    }

    public LocalDateTime getLastAppliedReportDate() {
        return lastAppliedReportDate;
    }
}
