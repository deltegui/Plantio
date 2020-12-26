package com.deltegui.plantio.game.domain;

import com.deltegui.plantio.weather.domain.WeatherReport;
import com.deltegui.plantio.weather.domain.WeatherState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.deltegui.plantio.game.domain.PlantMother.*;
import static com.deltegui.plantio.weather.domain.ReportMother.createReport;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GameEventNoticerTest {
    @Test
    public void shouldNotGenerateEventsIfNothingHappened() {
        var prev = createGame(
                createPlant(PlantType.WHEAT, 1)
        );
        var current = createGame(
                createPlant(PlantType.WHEAT, 1)
        );
        GameEventNoticer gameEventNoticer = new GameEventNoticer(prev);
        List<GameEvent> events = gameEventNoticer.generateEvents(current, Collections.emptyList());
        assertEquals(0, events.size());
    }

    @Test
    public void shouldNotGenerateEventsIfPlantsDontMatch() {
        var prev = createGame(
        );
        var current = createGame(
                createPlant(PlantType.WHEAT, 1)
        );
        GameEventNoticer gameEventNoticer = new GameEventNoticer(prev);
        List<GameEvent> events = gameEventNoticer.generateEvents(current, Collections.emptyList());
        assertEquals(0, events.size());
    }

    @Test
    public void shouldNoticeWhenAPlantGrows() {
        var prev = createGame(
                createPlant(PlantType.WHEAT, 1)
        );
        var current = createGame(
                createPlant(PlantType.WHEAT, 2)
        );
        GameEventNoticer gameEventNoticer = new GameEventNoticer(prev);
        List<GameEvent> events = gameEventNoticer.generateEvents(current, Collections.emptyList());
        assertEquals(1, events.size());
        assertTrue(events.get(0).isEventType(GameEventType.GROW));
    }

    @ParameterizedTest
    @MethodSource
    public void shouldNoticeWhenAPlantDies(Plant previous, Plant current, WeatherReport report, GameEventType expectedEventType) {
        var prevGame = createGame(previous);
        var currentGame = createGame(current);
        GameEventNoticer gameEventNoticer = new GameEventNoticer(prevGame);
        List<GameEvent> events = gameEventNoticer.generateEvents(currentGame, Collections.singletonList(report));
        assertEquals(1, events.size());
        assertTrue(events.get(0).isEventType(expectedEventType), "Unexpected event type");
    }

    public static Stream<Arguments> shouldNoticeWhenAPlantDies() {
        return Stream.of(
                arguments(
                        createPlant(100, PlantType.CACTUS, 2, WateredState.WET, 1),
                        createPlant(98.32, PlantType.CACTUS, 2, WateredState.WET, 6),
                        createReport(WeatherState.CLEAR, 1),
                        GameEventType.KILLED_TEMPERATURE_LOW
                ),
                arguments(
                        createPlant(100, PlantType.CACTUS, 2, WateredState.WET, 1),
                        createPlant(98.32, PlantType.CACTUS, 2, WateredState.WET, 6),
                        createReport(WeatherState.CLEAR, 120),
                        GameEventType.KILLED_TEMPERATURE_HIGH
                ),
                arguments(
                        createPlant(100, PlantType.CACTUS, 2, WateredState.DRY, 1),
                        createPlant(0, PlantType.CACTUS, 0, WateredState.DRY, 6),
                        createReport(WeatherState.CLEAR, 34),
                        GameEventType.KILLED_DRY
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    public void shouldNoticeWhenWateredOrDried(Plant previous, Plant current, GameEventType expectedEventType) {
        var prevGame = createGame(previous);
        var currentGame = createGame(current);
        GameEventNoticer gameEventNoticer = new GameEventNoticer(prevGame);
        List<GameEvent> events = gameEventNoticer.generateEvents(currentGame, Collections.singletonList(createReport(WeatherState.CLEAR, 30)));
        assertEquals(1, events.size());
        assertTrue(events.get(0).isEventType(expectedEventType), "Unexpected event type");
    }

    public static Stream<Arguments> shouldNoticeWhenWateredOrDried() {
        return Stream.of(
                arguments(
                        createPlant(100, PlantType.CACTUS, 2, WateredState.WET, 1),
                        createPlant(50, PlantType.CACTUS, 2, WateredState.DRY, 1),
                        GameEventType.DRIED
                ),
                arguments(
                        createPlant(50, PlantType.CACTUS, 2, WateredState.DRY, 1),
                        createPlant(100, PlantType.CACTUS, 2, WateredState.WET, 1),
                        GameEventType.WATERED
                )
        );
    }
}
