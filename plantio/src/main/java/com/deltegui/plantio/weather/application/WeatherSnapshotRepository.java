package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.weather.domain.UserWeatherSnapshot;

import java.util.List;

public interface WeatherSnapshotRepository {
    void save(UserWeatherSnapshot snapshot);
    List<UserWeatherSnapshot> getForUser(String user);
    void removeForUser(String user);
}
