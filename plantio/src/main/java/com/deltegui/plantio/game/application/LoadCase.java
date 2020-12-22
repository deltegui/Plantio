package com.deltegui.plantio.game.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.game.domain.Game;
import com.deltegui.plantio.weather.application.WeatherSnapshotRepository;
import com.deltegui.plantio.weather.domain.UserWeatherSnapshot;
import org.springframework.stereotype.Service;

@Service
public class LoadCase implements UseCase<LoadRequest, Game> {
    private final GameRepository gameRepository;
    private final WeatherSnapshotRepository snapshotRepository;

    public LoadCase(GameRepository gameRepository, WeatherSnapshotRepository snapshotRepository) {
        this.gameRepository = gameRepository;
        this.snapshotRepository = snapshotRepository;
    }

    @Override
    public Game handle(LoadRequest request) throws DomainException {
        var user = request.getUser();
        var game = this.gameRepository.load(user)
                .map(this::applyWeatherSnapshotsAndSave)
                .orElseGet(() -> this.createAndSaveNewGame(user));
        this.snapshotRepository.removeForUser(user);
        return game;
    }

    private Game createAndSaveNewGame(String user) {
        Game game = Game.createEmpty(user);
        this.gameRepository.save(game);
        return game;
    }

    private Game applyWeatherSnapshotsAndSave(Game game) {
        this.snapshotRepository.getForUser(game.getOwner())
                .stream()
                .map(UserWeatherSnapshot::getReport)
                .forEach(game::applyWeather);
        this.gameRepository.update(game);
        return game;
    }
}
