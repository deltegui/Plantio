package com.deltegui.plantio.game.domain.noticers;

import com.deltegui.plantio.game.domain.GameEvent;
import com.deltegui.plantio.game.domain.GameEventType;
import com.deltegui.plantio.game.domain.Plant;
import com.deltegui.plantio.weather.domain.WeatherReport;

import java.util.Collections;
import java.util.List;

public class GrowthNoticer implements Noticer<Plant> {
    @Override
    public List<GameEvent> generateEvents(Plant prev, Plant current, List<WeatherReport> appliedReports) {
        if (current.haveAdvancedPhaseThan(prev)) {
            return Collections.singletonList(new GameEvent(GameEventType.GROW, current.getPosition()));
        }
        return Collections.emptyList();
    }
}
