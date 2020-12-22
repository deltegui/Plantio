package com.deltegui.plantio.game.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.game.domain.Game;
import com.deltegui.plantio.game.domain.Plant;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SaveCase implements UseCase<SaveRequest, SaveResponse> {
    private final GameRepository gameRepository;

    public SaveCase(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public SaveResponse handle(SaveRequest request) throws DomainException {
        String user = request.getUser();
        return this.gameRepository.load(user)
                .map(game -> this.updateGame(game, request))
                .orElseGet(() -> this.createGame(user, request));
    }

    private SaveResponse createGame(String user, SaveRequest req) {
        Set<Plant> crop = req.getPlants()
                .stream()
                .map(PlantRequest::createNewPlant)
                .collect(Collectors.toSet());
        Game game = Game.createWithCrop(user, crop);
        this.gameRepository.save(game);
        return SaveResponse.fromGame(game);
    }

    private SaveResponse updateGame(Game game, SaveRequest req) {
        var crop = refreshPlants(game, req);
        game.replaceCrop(crop);
        this.gameRepository.update(game);
        return SaveResponse.fromGame(game);
    }

    private Set<Plant> refreshPlants(Game game, SaveRequest req) {
        var oldPlants = game.getCrop();
        Set<Plant> out = new HashSet<>();
        for (Plant plant : oldPlants) {
            req.createPlantIfExists(plant).ifPresent(out::add);
        }
        return out;
    }
}
