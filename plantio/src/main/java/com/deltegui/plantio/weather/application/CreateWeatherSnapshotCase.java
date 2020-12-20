package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.game.application.GameRepository;
import com.deltegui.plantio.game.domain.Game;
import com.deltegui.plantio.weather.domain.UserWeatherSnapshot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateWeatherSnapshotCase implements UseCase<Delayer, Void> {
    private final GameRepository gameRepository;
    private final WeatherSnapshotRepository snapshotRepository;
    private final ReadReportCase readReportCase;

    public CreateWeatherSnapshotCase(GameRepository gameRepository, WeatherSnapshotRepository snapshotRepository, ReadReportCase readReportCase) {
        this.gameRepository = gameRepository;
        this.snapshotRepository = snapshotRepository;
        this.readReportCase = readReportCase;
    }

    @Override
    public Void handle(Delayer delayer) throws DomainException {
        List<Game> games = this.gameRepository.getAllWithoutPlants();
        for (Game game : games) {
            game.getLastPosition().ifPresent(lastPosition -> {
                var report = this.readReportCase.handle(lastPosition);
                this.snapshotRepository.save(new UserWeatherSnapshot(game.getOwner(), report));
                delayer.delay();
            });
        }
        return null;
    }
}
