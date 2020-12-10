package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.weather.domain.Coordinate;
import com.deltegui.plantio.weather.domain.WeatherReport;
import org.springframework.stereotype.Service;

@Service
public class ReadReportCase implements UseCase<Coordinate, WeatherReport> {
    private final WeatherProvider weatherProvider;
    private final WeatherReportRepository reportRepository;

    public ReadReportCase(WeatherProvider weatherProvider, WeatherReportRepository reportRepository) {
        this.weatherProvider = weatherProvider;
        this.reportRepository = reportRepository;
    }

    @Override
    public WeatherReport handle(Coordinate location) throws DomainException {
        return this.reportRepository.find(location)
                .filter(report -> !report.isOld())
                .orElseGet(() -> readAndSaveFromProvider(location));
    }

    private WeatherReport readAndSaveFromProvider(Coordinate location) {
        WeatherReport report = this.weatherProvider.read(location)
                .orElseThrow(() -> DomainException.fromError(WeatherErrors.Read));
        this.reportRepository.saveOrReplace(report);
        return report;
    }
}
