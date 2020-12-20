package com.deltegui.plantio.weather.implementation;

import com.deltegui.plantio.weather.application.ReadAndUpdateRequest;
import com.deltegui.plantio.weather.application.ReadReportCase;
import com.deltegui.plantio.weather.application.ReadWeatherAndUpdateLocationCase;
import com.deltegui.plantio.weather.domain.Coordinate;
import com.deltegui.plantio.weather.domain.WeatherReport;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final ReadWeatherAndUpdateLocationCase readReportCase;

    public WeatherController(ReadWeatherAndUpdateLocationCase readReportCase) {
        this.readReportCase = readReportCase;
    }

    @GetMapping("/read")
    public WeatherReport readReport(
            Principal user,
            @NotNull @RequestParam double latitude,
            @NotNull @RequestParam double longitude
    ) {
        return readReportCase.handle(new ReadAndUpdateRequest(
                user.getName(),
                new Coordinate(latitude, longitude)
        ));
    }
}
