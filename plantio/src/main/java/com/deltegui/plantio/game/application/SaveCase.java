package com.deltegui.plantio.game.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.game.domain.Game;
import com.deltegui.plantio.game.domain.Plant;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SaveCase implements UseCase<SaveRequest, SaveResponse> {
    private final GameRepository gameRepository;

    public SaveCase(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public SaveResponse handle(SaveRequest request) throws DomainException {
        String user = request.getUser();
        Set<Plant> crop = request.getPlants();
        return this.gameRepository.load(user)
                .map(game -> this.updateGame(game, crop))
                .orElse(this.createGame(user, crop));
    }

    private SaveResponse createGame(String user, Set<Plant> crop) {
        Game game = Game.createWithCrop(user, crop);
        this.gameRepository.save(game);
        return SaveResponse.fromGame(game);
    }

    private SaveResponse updateGame(Game game, Set<Plant> crop) {
        game.replaceCrop(crop);
        this.gameRepository.save(game);
        return SaveResponse.fromGame(game);
    }
}
