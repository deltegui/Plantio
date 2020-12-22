package com.deltegui.plantio.weather.domain;

import javax.validation.constraints.NotNull;

public class TemperatureInterval {
    private @NotNull final double min;
    private @NotNull final double max;

    private TemperatureInterval(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public static TemperatureInterval withMinMax(double min, double max) {
        return new TemperatureInterval(min, max);
    }

    public boolean includes(double other) {
        return other >= this.min && other <= this.max;
    }
}
