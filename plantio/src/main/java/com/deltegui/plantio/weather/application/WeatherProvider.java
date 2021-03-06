package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.weather.domain.Coordinate;
import com.deltegui.plantio.weather.domain.WeatherReport;

import java.util.Optional;

public interface WeatherProvider {
    Optional<WeatherReport> read(Coordinate location);
}
