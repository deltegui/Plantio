package com.deltegui.plantio.weather.implementation;

import com.deltegui.plantio.weather.application.ReadReportCase;
import com.deltegui.plantio.weather.domain.WeatherReport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final ReadReportCase readReportCase;

    public WeatherController(ReadReportCase readReportCase) {
        this.readReportCase = readReportCase;
    }

    @GetMapping("/read/{location}")
    public WeatherReport readReport(@NotNull @PathVariable String location) {
        return readReportCase.handle(location);
    }
}
