package com.deltegui.plantio.game.domain;

import com.deltegui.plantio.game.domain.noticers.DeadNoticer;
import com.deltegui.plantio.game.domain.noticers.GrowthNoticer;
import com.deltegui.plantio.game.domain.noticers.Noticer;
import com.deltegui.plantio.game.domain.noticers.WateredNoticer;
import com.deltegui.plantio.weather.domain.WeatherReport;

import java.util.*;
import java.util.stream.Collectors;

public class GameEventNoticer {
    private final Game previous;
    private static final List<Noticer<Plant>> plantGenerators = new ArrayList<>();

    static {
        plantGenerators.addAll(Arrays.asList(
                new GrowthNoticer(),
                new DeadNoticer(),
                new WateredNoticer()
        ));
    }

    public GameEventNoticer(Game previous) {
        this.previous = previous.copy();
    }

    public List<GameEvent> generateEvents(Game current, List<WeatherReport> appliedReports) {
        var currentCrop = current.getCrop();
        List<GameEvent> events = new ArrayList<>();
        for (Plant currentPlant : currentCrop) {
            getPreviousPlant(currentPlant).ifPresent(prev ->
                    events.addAll(this.applyPlantNoticers(prev, currentPlant, appliedReports)));
        }
        return events;
    }

    private Optional<Plant> getPreviousPlant(Plant current) {
        var crop = this.previous.getCrop();
        for (Plant prev : crop) {
            if (prev.equals(current)) {
                return Optional.of(prev);
            }
        }
        return Optional.empty();
    }

    private List<GameEvent> applyPlantNoticers(Plant previous, Plant current, List<WeatherReport> appliedReports) {
        return plantGenerators
                .stream()
                .map(plantNoticer -> plantNoticer.generateEvents(previous, current, appliedReports))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
