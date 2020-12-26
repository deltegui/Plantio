package com.deltegui.plantio.game.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.game.domain.Game;
import com.deltegui.plantio.game.domain.GameEventNoticer;
import com.deltegui.plantio.weather.application.WeatherSnapshotRepository;
import com.deltegui.plantio.weather.domain.UserWeatherSnapshot;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class LoadCase implements UseCase<LoadRequest, LoadResponse> {
    private final GameRepository gameRepository;
    private final WeatherSnapshotRepository snapshotRepository;

    public LoadCase(GameRepository gameRepository, WeatherSnapshotRepository snapshotRepository) {
        this.gameRepository = gameRepository;
        this.snapshotRepository = snapshotRepository;
    }

    @Override
    public LoadResponse handle(LoadRequest request) throws DomainException {
        var user = request.getUser();
        var game = this.gameRepository.load(user)
                .map(this::applyWeatherSnapshotsAndSave)
                .orElseGet(() -> this.createAndSaveNewGame(user));
        this.snapshotRepository.removeForUser(user);
        return game;
    }

    private LoadResponse createAndSaveNewGame(String user) {
        Game game = Game.createEmpty(user);
        this.gameRepository.save(game);
        return new LoadResponse(game, Collections.emptyList());
    }

    private LoadResponse applyWeatherSnapshotsAndSave(Game game) {
        var eventNoticer = new GameEventNoticer(game);
        var reports = this.snapshotRepository.getForUser(game.getOwner())
                .stream()
                .map(UserWeatherSnapshot::getReport)
                .collect(Collectors.toList());
        reports.forEach(game::applyWeather);
        this.gameRepository.update(game);
        var events = eventNoticer.generateEvents(game, reports);
        return new LoadResponse(game, events);
    }
}
