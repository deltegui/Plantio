package com.deltegui.plantio.game.implementation;

import com.deltegui.plantio.game.application.GameRepository;
import com.deltegui.plantio.game.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void save(Game game) {
        this.jdbcTemplate.update(
                "insert into saves (save_user, last_update) values(?, ?)",
                game.getOwner(),
                game.getLastUpdate());
        this.savePlants(game);
    }

    @Transactional
    @Override
    public void update(Game game) {
        this.jdbcTemplate.update(
                "update saves set last_update = ? where save_user = ?",
                game.getLastUpdate(),
                game.getOwner());
        this.jdbcTemplate.update("delete from saved_plants where save_user = ?", game.getOwner());
        this.savePlants(game);
    }

    private void savePlants(Game game) {
        game.getCrop().forEach(plant -> this.jdbcTemplate.update(
                "insert into saved_plants (save_user, pos_x, pos_y, plant_name, phase, watered, humidity, hours_wet, last_applied_report_date) values(?, ?, ?, ?, ?, ?, ?, ? ,?)",
                game.getOwner(),
                plant.getPosition().getX(),
                plant.getPosition().getY(),
                plant.getType().name(),
                plant.getPhase(),
                plant.getWatered().name(),
                plant.getHumidity(),
                plant.getHoursWet(),
                plant.getLastAppliedReportDate()
        ));
    }

    @Override
    public Optional<Game> load(String userName) {
        List<Game> games = this.jdbcTemplate.query(
                "select save_user, last_update from saves where save_user = ?",
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
                "select pos_x, pos_y, plant_name, phase, watered, humidity, hours_wet, last_applied_report_date from saved_plants where save_user = ?",
                (resultSet, number) -> new Plant(
                        PlantType.valueOf(resultSet.getNString("plant_name")),
                        WateredState.valueOf(resultSet.getNString("watered")),
                        resultSet.getInt("phase"),
                        new Position(resultSet.getInt("pos_x"), resultSet.getInt("pos_y")),
                        resultSet.getDouble("humidity"),
                        resultSet.getInt("hours_wet"),
                        resultSet.getTimestamp("last_applied_report_date").toLocalDateTime()
                ),
                userName
        );
    }

    private Game parseGameFromQueryResult(ResultSet resultSet, int number) throws SQLException {
        final var saveUser = resultSet.getString("save_user");
        final var lastUpdate =resultSet.getTimestamp("last_update").toLocalDateTime();
        return new Game(
                saveUser,
                lastUpdate,
                null
        );
    }
}
