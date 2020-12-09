package com.deltegui.plantio.game.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.game.domain.Game;
import org.springframework.stereotype.Service;

@Service
public class LoadCase implements UseCase<LoadRequest, Game> {
    private final GameRepository gameRepository;

    public LoadCase(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game handle(LoadRequest request) throws DomainException {
        var user = request.getUser();
        return this.gameRepository.load(user).orElseGet(() -> {
            Game game = Game.createEmpty(user);
            this.gameRepository.save(game);
            return game;
        });
    }
}
