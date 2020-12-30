package com.deltegui.plantio.weather.implementation;

import com.deltegui.plantio.weather.application.WeatherReportRepository;
import com.deltegui.plantio.weather.domain.Coordinate;
import com.deltegui.plantio.weather.domain.WeatherReport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryWeatherReportRepository implements WeatherReportRepository {
    private final List<WeatherReport> data;

    public MemoryWeatherReportRepository() {
        this.data = new ArrayList<>();
    }

    @Override
    public void saveOrReplace(WeatherReport report) {
        find(report.getCoordinate()).ifPresent(data::remove);
        data.add(report);
    }

    @Override
    public Optional<WeatherReport> find(Coordinate location) {
        return data.stream()
                .filter(report -> report.isNearTo(location))
                .findFirst();
    }
}
