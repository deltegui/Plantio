package com.deltegui.plantio.game.domain.noticers;

import com.deltegui.plantio.game.domain.GameEvent;
import com.deltegui.plantio.weather.domain.WeatherReport;

import java.util.List;

public interface Noticer<T> {
    List<GameEvent> generateEvents(T prev, T current, List<WeatherReport> appliedReports);
}
