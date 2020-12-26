package com.deltegui.plantio.game.domain.noticers;

import com.deltegui.plantio.game.domain.GameEvent;
import com.deltegui.plantio.game.domain.GameEventType;
import com.deltegui.plantio.game.domain.Plant;
import com.deltegui.plantio.game.domain.WateredState;
import com.deltegui.plantio.weather.domain.WeatherReport;

import java.util.Collections;
import java.util.List;

public class WateredNoticer implements Noticer<Plant> {

    @Override
    public List<GameEvent> generateEvents(Plant prev, Plant current, List<WeatherReport> appliedReports) {
        if (haveJustGetDried(prev, current)) {
            return Collections.singletonList(new GameEvent(
                    GameEventType.DRIED,
                    current.getPosition()
            ));
        }
        if (haveJustGetWet(prev, current)) {
            return Collections.singletonList(new GameEvent(
                    GameEventType.WATERED,
                    current.getPosition()
            ));
        }
        return Collections.emptyList();
    }

    private boolean haveJustGetWet(Plant prev, Plant current) {
        return (prev.getWatered() == WateredState.DRY) && current.getWatered() == WateredState.WET;
    }

    private boolean haveJustGetDried(Plant prev, Plant current) {
        return (prev.getWatered() == WateredState.WET) && current.getWatered() == WateredState.DRY;
    }
}
