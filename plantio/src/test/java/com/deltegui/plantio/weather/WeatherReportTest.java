package com.deltegui.plantio.weather;

import com.deltegui.plantio.game.domain.Plant;
import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.weather.domain.WeatherReport;
import com.deltegui.plantio.weather.domain.WeatherState;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.deltegui.plantio.game.domain.PlantMother.createPlant;
import static com.deltegui.plantio.weather.domain.ReportMother.createReport;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class WeatherReportTest {
    @ParameterizedTest
    @MethodSource
    public void shouldCalculateHumidity(Plant plant, WeatherReport report, double expected) {
        assertEquals(expected, report.calculateHumidity(plant));
    }

    public static Stream<Arguments> shouldCalculateHumidity() {
        return Stream.of(
                arguments(
                        createPlant(100, PlantType.WHEAT, 6),
                        createReport(WeatherState.CLEAR, 24),
                        79.35
                ),
                arguments(
                        createPlant(100, PlantType.WHEAT, 1),
                        createReport(WeatherState.CLEAR, 24),
                        96.15
                ),
                arguments(
                        createPlant(50, PlantType.CACTUS, 6),
                        createReport(WeatherState.RAIN, 30),
                        100.0
                ),
                arguments(
                        createPlant(50, PlantType.WHEAT, 20),
                        createReport(WeatherState.CLEAR, 30),
                        0
                ),
                arguments(
                        createPlant(0, PlantType.WHEAT, 1),
                        createReport(WeatherState.CLEAR, 30),
                        0
                ),
                arguments(
                        createPlant(50, PlantType.WHEAT, 20),
                        createReport(WeatherState.RAIN, 30),
                        100
                ),
                arguments(
                        createPlant(100, PlantType.WHEAT, 1),
                        createReport(WeatherState.RAIN, 30),
                        100
                ),
                arguments(
                        createPlant(50, PlantType.WHEAT, 6),
                        createReport(WeatherState.CLEAR, -2),
                        49.16
                )
        );
    }
}
