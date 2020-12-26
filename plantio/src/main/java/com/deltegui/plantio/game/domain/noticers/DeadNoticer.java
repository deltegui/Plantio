package com.deltegui.plantio.game.domain.noticers;

import com.deltegui.plantio.game.domain.GameEvent;
import com.deltegui.plantio.game.domain.Plant;
import com.deltegui.plantio.weather.domain.WeatherReport;

import java.util.Collections;
import java.util.List;

public class DeadNoticer implements Noticer<Plant> {
    @Override
    public List<GameEvent> generateEvents(Plant prev, Plant current, List<WeatherReport> appliedReports) {
        if (! haveJustDied(prev, current)) {
            return Collections.emptyList();
        }
        return this.generateDeadEvent(current, appliedReports);
    }

    private List<GameEvent> generateDeadEvent(Plant current, List<WeatherReport> appliedReports) {
        for (var report : appliedReports) {
            var optionalDead = current.getDeadReason(report);
            if (optionalDead.isPresent()) {
                return Collections.singletonList(new GameEvent(
                        optionalDead.get(),
                        current.getPosition()
                ));
            }
        }
        return Collections.emptyList();
    }

    private boolean haveJustDied(Plant prev, Plant current) {
        return (! prev.isDied()) && current.isDied();
    }
}
