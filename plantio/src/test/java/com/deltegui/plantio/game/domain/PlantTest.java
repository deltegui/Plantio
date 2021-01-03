package com.deltegui.plantio.game.domain;

import com.deltegui.plantio.weather.domain.WeatherReport;
import com.deltegui.plantio.weather.domain.WeatherState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static com.deltegui.plantio.game.domain.PlantMother.createPlant;
import static com.deltegui.plantio.game.domain.PlantMother.deadPlant;
import static com.deltegui.plantio.weather.domain.ReportMother.createReport;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PlantTest {
    @Test
    public void deadPlantShouldStayDead() {
        var plant = deadPlant();
        var report = createReport(WeatherState.CLEAR, 20);
        plant.applyWeather(report);
        assertEquals(Plant.DEATH_PHASE, plant.getPhase());
    }

    @ParameterizedTest
    @MethodSource
    public void plantShouldGrowIfIsWetAndPassEnoughTime(Plant plant, WeatherReport report, int expectedPhase) {
        plant.applyWeather(report);
        assertEquals(expectedPhase, plant.getPhase());
    }

    public static Stream<Arguments> plantShouldGrowIfIsWetAndPassEnoughTime() {
        return Stream.of(
                arguments(
                        createPlant(100, PlantType.WHEAT, 6, WateredState.WET),
                        createReport(WeatherState.RAIN, 20),
                        0
                ),
                arguments(
                        createPlant(100, PlantType.WHEAT, Duration.ofHours(27).toHours(), WateredState.WET),
                        createReport(WeatherState.RAIN, 20),
                        3
                ),
                arguments(
                        createPlant(100, PlantType.WHEAT, Duration.ofDays(5).toHours(), WateredState.WET),
                        createReport(WeatherState.RAIN, 20),
                        5
                ),
                arguments(
                        createPlant(100, PlantType.WHEAT, Duration.ofDays(5).toHours(), WateredState.WET, 4),
                        createReport(WeatherState.RAIN, 20),
                        Plant.END_PHASE
                )
        );
    }

    @Test
    public void dryPlantDontGrow() {
        var plant = createPlant(100, PlantType.WHEAT, Duration.ofDays(1).toHours(), WateredState.DRY);
        var report = createReport(WeatherState.CLEAR, 17);
        plant.applyWeather(report);
        assertEquals(0, plant.getPhase());
    }

    @Test
    public void dryPlantIfWateredGrows() {
        var plant = createPlant(21, PlantType.WHEAT, Duration.ofDays(7).toHours(), WateredState.DRY);
        var report = createReport(WeatherState.RAIN, 20, Duration.ofDays(5).toHours());
        var later = createReport(WeatherState.RAIN, 22, Duration.ofDays(3).toHours());
        plant.applyWeather(report);
        plant.applyWeather(later);
        assertEquals(5, plant.getPhase());
    }

    @ParameterizedTest
    @MethodSource
    public void plantShouldLiveIfIsInsideTemperatureInterval(Plant plant, double temperature, int expectedPhase) {
        var report = createReport(WeatherState.CLEAR, temperature);
        plant.applyWeather(report);
        assertEquals(expectedPhase, plant.getPhase());
    }

    public static Stream<Arguments> plantShouldLiveIfIsInsideTemperatureInterval() {
        return Stream.of(
                arguments(
                        createPlant(100, PlantType.CACTUS, 6),
                        26,
                        0
                ),
                arguments(
                        createPlant(100, PlantType.CACTUS, 6),
                        80,
                        Plant.DEATH_PHASE
                ),
                arguments(
                        createPlant(100, PlantType.CACTUS, 6),
                        20,
                        Plant.DEATH_PHASE
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    public void plantShouldDeadIfHumidityIsLow(Plant plant) {
        var report = createReport(WeatherState.CLEAR, 30);
        plant.applyWeather(report);
        assertEquals(Plant.DEATH_PHASE, plant.getPhase());
    }

    public static Stream<Arguments> plantShouldDeadIfHumidityIsLow() {
        return Stream.of(
                arguments(createPlant(3, PlantType.CACTUS, 1)),
                arguments(createPlant(20, PlantType.WHEAT, 6))
        );
    }

}
