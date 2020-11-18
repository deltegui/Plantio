package com.deltegui.plantio.weather.implementation;

import com.deltegui.plantio.weather.application.WeatherReportRepository;
import com.deltegui.plantio.weather.domain.WeatherReport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryWeatherReportRepository implements WeatherReportRepository {
    private final Map<String, WeatherReport> data;

    public MemoryWeatherReportRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public void saveOrReplace(WeatherReport report) {
        this.data.put(report.getLocation(), report);
    }

    @Override
    public Optional<WeatherReport> findForLocation(String location) {
        var report = this.data.get(location);
        return report == null ? Optional.empty() : Optional.of(report);
    }
}
