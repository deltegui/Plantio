package com.deltegui.plantio.game.implementation;

import com.deltegui.plantio.game.application.GameRepository;
import com.deltegui.plantio.game.domain.Game;
import com.deltegui.plantio.game.domain.Plant;
import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.game.domain.WateredState;
import com.deltegui.plantio.weather.domain.Coordinate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        game.getLastPosition().ifPresentOrElse(
                (Coordinate lastPosition) -> this.jdbcTemplate.update(
                        "insert into saves (save_user, last_update, latitude, longitude) values(?, ?, ?, ?)",
                        game.getOwner(),
                        game.getLastUpdate(),
                        lastPosition.getLatitude(),
                        lastPosition.getLongitude()
                ),
                () -> this.jdbcTemplate.update(
                        "insert into saves (save_user, last_update) values(?, ?)",
                        game.getOwner(),
                        game.getLastUpdate()
                )
        );
        this.savePlants(game);
    }

    @Override
    public void update(Game game) {
        game.getLastPosition().ifPresentOrElse(
                (lastPos) -> this.jdbcTemplate.update(
                        "update saves set last_update = ?, latitude = ?, longitude = ? where save_user = ?",
                        game.getLastUpdate(),
                        lastPos.getLatitude(),
                        lastPos.getLongitude(),
                        game.getOwner()
                ),
                () -> this.jdbcTemplate.update(
                        "update saves set last_update = ? where save_user = ?",
                        game.getLastUpdate(),
                        game.getOwner()
                )
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
                "select save_user, last_update, latitude, longitude from saves where save_user = ?",
                this::parseGameFromQueryResult,
                userName
        );
        if (games.size() <= 0) {
            return Optional.empty();
        }
        var plants = this.selectPlantsForUser(userName);
        Game game = games.get(0);
        game.setCrop(new HashSet<>(plants));
        return Optional.of(game);
    }

    private List<Plant> selectPlantsForUser(String userName) {
        return this.jdbcTemplate.query(
                "select pos_x, pos_y, plant_name, phase, watered from saved_plants where save_user = ?",
                (resultSet, number) -> new Plant(
                        PlantType.fromString(resultSet.getNString("plant_name")),
                        WateredState.fromString(resultSet.getNString("watered")),
                        resultSet.getInt("phase"),
                        new Plant.Position(resultSet.getInt("pos_x"), resultSet.getInt("pos_y"))
                ),
                userName
        );
    }

    @Override
    public List<Game> getAllWithoutPlants() {
        return this.jdbcTemplate.query(
                "select save_user, last_update, latitude, longitude from saves",
                this::parseGameFromQueryResult
        );
    }

    private Game parseGameFromQueryResult(ResultSet resultSet, int number) throws SQLException {
        final var latitude = resultSet.getObject("latitude");
        final var longitude = resultSet.getObject("longitude");
        final var saveUser = resultSet.getString("save_user");
        final var lastUpdate =resultSet.getTimestamp("last_update").toLocalDateTime();
        if (latitude != null && longitude != null) {
            return new Game(
                    saveUser,
                    lastUpdate,
                    new Coordinate((Float)latitude, (Float)longitude),
                    null);
        }
        return new Game(
                saveUser,
                lastUpdate,
                null,
                null
        );
    }
}
