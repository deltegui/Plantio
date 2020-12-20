package com.deltegui.plantio.game.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.game.domain.Game;
import com.deltegui.plantio.weather.application.WeatherSnapshotRepository;
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
        this.snapshotRepository.removeForUser(user);
        return this.gameRepository.load(user).orElseGet(() -> {
            Game game = Game.createEmpty(user);
            this.gameRepository.save(game);
            return game;
        });
    }
}
