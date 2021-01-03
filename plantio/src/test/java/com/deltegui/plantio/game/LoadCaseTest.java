package com.deltegui.plantio.game;

import com.deltegui.plantio.MemoryGameRepository;
import com.deltegui.plantio.game.application.GameRepository;
import com.deltegui.plantio.game.application.LoadCase;
import com.deltegui.plantio.game.application.LoadRequest;
import com.deltegui.plantio.game.domain.*;
import com.deltegui.plantio.weather.application.WeatherSnapshotRepository;
import com.deltegui.plantio.weather.domain.UserWeatherSnapshot;
import com.deltegui.plantio.weather.domain.WeatherState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

import static com.deltegui.plantio.game.domain.PlantMother.*;
import static com.deltegui.plantio.weather.domain.ReportMother.createReport;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoadCaseTest {
    private final LoadRequest request = new LoadRequest("manolo");

    @Test
    public void loadShouldLoadGame() {
        var expectedGame = createGame(
                createPlant(PlantType.CACTUS, 0)
        );
        var gameRepo = mock(GameRepository.class);
        when(gameRepo.load(anyString())).thenReturn(Optional.of(expectedGame));
        var snapshotRepo = mock(WeatherSnapshotRepository.class);
        var loadCase = new LoadCase(gameRepo, snapshotRepo);
        assertEquals(expectedGame, loadCase.handle(request).getGame());
    }

    @Test
    public void ifUserHaveNotGameShouldCreateAnEmptyOne() {
        var gameRepo = mock(GameRepository.class);
        when(gameRepo.load(anyString())).thenReturn(Optional.empty());
        var snapshotRepo = mock(WeatherSnapshotRepository.class);
        var loadCase = new LoadCase(gameRepo, snapshotRepo);
        var response = loadCase.handle(request);
        assertEquals("manolo", response.getGame().getOwner());
        assertEquals(0, response.getGame().getCrop().size());
    }

    @ParameterizedTest
    @MethodSource
    public void shouldApplyWeatherSnapshots(Plant original, Plant expected, UserWeatherSnapshot[] snapshots) {
        var originalGame = createGame(original);
        var expectedGame = createGame(expected);

        var gameRepo = mock(GameRepository.class);
        when(gameRepo.load(anyString())).thenReturn(Optional.of(originalGame));
        var snapshotRepo = mock(WeatherSnapshotRepository.class);
        when(snapshotRepo.getForUser(anyString())).thenReturn(Arrays.asList(snapshots));

        var loadCase = new LoadCase(gameRepo, snapshotRepo);
        var response = loadCase.handle(request);

        assertEquals(expectedGame.getCrop().toArray()[0], response.getGame().getCrop().toArray()[0]);
    }

    public static Stream<Arguments> shouldApplyWeatherSnapshots() {
        return Stream.of(
                arguments(
                        createPlant(100, PlantType.CACTUS, 6, WateredState.WET, 1),
                        createPlant(98.32, PlantType.CACTUS, 4, WateredState.WET, 1),
                        new UserWeatherSnapshot[]{
                                toSnapshot(createReport(WeatherState.CLOUDS, 26, 4)),
                                toSnapshot(createReport(WeatherState.CLEAR, 26, 2))
                        }
                ),
                arguments(
                        createPlant(50, PlantType.WHEAT, 7, WateredState.DRY, 2),
                        createPlant(39.62, PlantType.WHEAT, 2, WateredState.WET, 2),
                        new UserWeatherSnapshot[]{
                                toSnapshot(createReport(WeatherState.CLOUDS, 26, 4)),
                                toSnapshot(createReport(WeatherState.CLEAR, 26, 2))
                        }
                ),
                arguments(
                        deadPlant(),
                        deadPlant(),
                        new UserWeatherSnapshot[]{
                                toSnapshot(createReport(WeatherState.CLOUDS, 26, 4)),
                                toSnapshot(createReport(WeatherState.CLEAR, 26, 2))
                        }
                )
        );
    }

    @Test
    public void shouldNotDeleteAfterManyLoads() {
        var expectedGame = createGame(
                createPlant(100, PlantType.CACTUS, 6, WateredState.WET, 1),
                createPlant(98.32, PlantType.WHEAT, 4, WateredState.WET, 1),
                createPlant(98.32, PlantType.WHEAT, 4, WateredState.WET, 1),
                createPlant(91.32, PlantType.WHEAT, 4, WateredState.WET, 1)
        );

        var gameRepo = MemoryGameRepository.withGames(expectedGame);
        var snapshotRepo = mock(WeatherSnapshotRepository.class);

        var loadCase = new LoadCase(gameRepo, snapshotRepo);
        for (int i = 0; i < 200; i++) {
            loadCase.handle(request);
        }

        assertEquals(4, gameRepo.getElement(0).getCrop().size());
    }

    @Test
    public void shouldGrowPlantsIfPassEnoughtHoursWet() {
        var game = createGame(
                new Plant(
                        PlantType.WHEAT,
                        WateredState.WET,
                        0,
                        new Position(0 ,0),
                        100,
                        9,
                        LocalDateTime.now().minus(1, ChronoUnit.SECONDS)
                )
        );

        var gameRepo = MemoryGameRepository.withGames(game);
        var snapshotRepo = mock(WeatherSnapshotRepository.class);
        when(snapshotRepo.getForUser(anyString())).thenReturn(Collections.singletonList(
                toSnapshot(createReport(WeatherState.CLOUDS, 26, 4))
        ));

        var loadCase = new LoadCase(gameRepo, snapshotRepo);
        var response = loadCase.handle(request);
        var plants = response.getGame().getCrop().toArray();
        var firstPlant = (Plant)plants[0];

        assertEquals(1, firstPlant.getPhase());
    }
}
