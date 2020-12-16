package com.deltegui.plantio.weather.implementation;

import com.deltegui.plantio.weather.application.ReadReportCase;
import com.deltegui.plantio.weather.domain.Coordinate;
import com.deltegui.plantio.weather.domain.WeatherReport;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final ReadReportCase readReportCase;

    public WeatherController(ReadReportCase readReportCase) {
        this.readReportCase = readReportCase;
    }

    @GetMapping("/read")
    public WeatherReport readReport(
            @NotNull @RequestParam double latitude,
            @NotNull @RequestParam double longitude
    ) {
        return readReportCase.handle(new Coordinate(latitude, longitude));
    }
}
