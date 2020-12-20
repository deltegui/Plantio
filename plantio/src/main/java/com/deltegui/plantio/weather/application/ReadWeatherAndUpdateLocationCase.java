package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.game.application.GameRepository;
import com.deltegui.plantio.weather.domain.WeatherReport;
import org.springframework.stereotype.Service;

@Service
public class ReadWeatherAndUpdateLocationCase implements UseCase<ReadAndUpdateRequest, WeatherReport> {
    private final ReadReportCase readReportCase;
    private final GameRepository gameRepository;

    public ReadWeatherAndUpdateLocationCase(ReadReportCase readReportCase, GameRepository gameRepository) {
        this.readReportCase = readReportCase;
        this.gameRepository = gameRepository;
    }

    @Override
    public WeatherReport handle(ReadAndUpdateRequest request) throws DomainException {
        this.gameRepository.load(request.getUsername()).ifPresent((game -> {
            game.setLastPosition(request.getCoordinate());
            this.gameRepository.update(game);
        }));
        return this.readReportCase.handle(request.getCoordinate());
    }
}
