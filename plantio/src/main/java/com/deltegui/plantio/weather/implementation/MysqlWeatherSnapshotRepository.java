package com.deltegui.plantio.weather.implementation;

import com.deltegui.plantio.weather.application.WeatherSnapshotRepository;
import com.deltegui.plantio.weather.domain.Coordinate;
import com.deltegui.plantio.weather.domain.UserWeatherSnapshot;
import com.deltegui.plantio.weather.domain.WeatherReport;
import com.deltegui.plantio.weather.domain.WeatherState;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlWeatherSnapshotRepository implements WeatherSnapshotRepository {
    private final JdbcTemplate jdbcTemplate;

    public MysqlWeatherSnapshotRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(UserWeatherSnapshot snapshot) {
        final var report = snapshot.getReport();
        this.jdbcTemplate.update(
                "insert into weather_snapshots (user, creation, location, latitude, longitude, weather_state, temperature, sunrise, sunset) values(?,?,?,?,?,?,?,?,?)",
                snapshot.getUserName(),
                report.getCreation(),
                report.getLocation(),
                report.getCoordinate().getLatitude(),
                report.getCoordinate().getLongitude(),
                report.getState().name(),
                report.getTemperature(),
                report.getSunrise(),
                report.getSunset()
        );
    }

    @Override
    public List<UserWeatherSnapshot> getForUser(String user) {
        return this.jdbcTemplate.query(
                "select creation, location, latitude, longitude, weather_state, temperature, sunrise, sunset from weather_snapshots where user = ?",
                (rs, number) -> new UserWeatherSnapshot(
                        user,
                        new WeatherReport(
                                new Coordinate(rs.getFloat("latitude"), rs.getFloat("longitude")),
                                rs.getString("location"),
                                WeatherState.fromString(rs.getString("weather_state")),
                                rs.getFloat("temperature"),
                                rs.getInt("sunrise"),
                                rs.getInt("sunset")
                        )
                ),
                user
        );
    }

    @Override
    public void removeForUser(String user) {
        this.jdbcTemplate.update("delete from weather_snapshots where user = ?", user);
    }
}
