package com.deltegui.plantio.game.implementation;

import com.deltegui.plantio.game.application.GameRepository;
import com.deltegui.plantio.game.domain.Game;
import com.deltegui.plantio.game.domain.Plant;
import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.game.domain.WateredState;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public class MysqlGameRepository implements GameRepository {
    private final JdbcTemplate jdbcTemplate;

    public MysqlGameRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Game game) {
        this.jdbcTemplate.update(
                "insert into saves (save_user, last_update) values(?, ?)",
                game.getOwner(),
                game.getLastUpdate()
        );
        this.savePlants(game);
    }

    @Override
    public void update(Game game) {
        this.jdbcTemplate.update(
                "update saves set last_update = ?",
                game.getLastUpdate()
        );
        this.jdbcTemplate.update("delete from saved_plants where save_user = ?", game.getOwner());
        this.savePlants(game);
    }

    private void savePlants(Game game) {
        game.getCrop()
                .parallelStream()
                .forEach(plant -> this.jdbcTemplate.update(
                        "insert into saved_plants (save_user, pos_x, pos_y, plant_name, phase, watered) values(?, ?, ?, ?, ?, ?)",
                        game.getOwner(),
                        plant.getPosition().getX(),
                        plant.getPosition().getY(),
                        plant.getType().name(),
                        plant.getPhase(),
                        plant.getWatered().name()
                ));
    }

    @Override
    public Optional<Game> load(String userName) {
        List<Game> games = this.jdbcTemplate.query(
                "select last_update from saves where save_user = ?",
                (resultSet, number) -> new Game(
                        userName,
                        resultSet.getTimestamp("last_update").toLocalDateTime(),
                        new HashSet<>()),
                userName
        );
        if (games.size() <= 0) {
            return Optional.empty();
        }
        var plants = this.jdbcTemplate.query(
                "select pos_x, pos_y, plant_name, phase, watered from saved_plants where save_user = ?",
                (resultSet, number) -> new Plant(
                        PlantType.fromString(resultSet.getNString("plant_name")),
                        WateredState.fromString(resultSet.getNString("watered")),
                        resultSet.getInt("phase"),
                        new Plant.Position(resultSet.getInt("pos_x"), resultSet.getInt("pos_y"))
                ),
                userName
        );
        Game game = games.get(0);
        game.replaceCrop(new HashSet<>(plants));
        return Optional.of(game);
    }
}
