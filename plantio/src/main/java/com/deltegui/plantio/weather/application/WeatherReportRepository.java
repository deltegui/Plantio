package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.weather.domain.Coordinate;
import com.deltegui.plantio.weather.domain.WeatherReport;

import java.util.Optional;

public interface WeatherReportRepository {
    void saveOrReplace(WeatherReport report);
    Optional<WeatherReport> find(Coordinate location);
}
