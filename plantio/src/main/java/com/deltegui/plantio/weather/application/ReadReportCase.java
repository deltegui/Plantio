package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.weather.domain.WeatherReport;
import org.springframework.stereotype.Service;

@Service
public class ReadReportCase implements UseCase<String, WeatherReport> {
    private final WeatherProvider weatherProvider;
    private final WeatherReportRepository reportRepository;

    public ReadReportCase(WeatherProvider weatherProvider, WeatherReportRepository reportRepository) {
        this.weatherProvider = weatherProvider;
        this.reportRepository = reportRepository;
    }

    @Override
    public WeatherReport handle(String location) throws DomainException {
        var optionalReport = this.reportRepository.findForLocation(location);
        if (optionalReport.isEmpty()) {
            return readAndSaveFromProvider(location);
        }
        var report = optionalReport.get();
        if (report.isOld()) {
            return readAndSaveFromProvider(location);
        }
        return report;
    }

    private WeatherReport readAndSaveFromProvider(String location) {
        WeatherReport report = this.weatherProvider.read(location)
                .orElseThrow(() -> DomainException.fromError(WeatherErrors.Read));
        this.reportRepository.saveOrReplace(report);
        return report;
    }
}
