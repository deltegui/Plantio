package com.deltegui.plantio.weather;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.weather.application.ReadReportCase;
import com.deltegui.plantio.weather.application.WeatherErrors;
import com.deltegui.plantio.weather.application.WeatherProvider;
import com.deltegui.plantio.weather.application.WeatherReportRepository;
import com.deltegui.plantio.weather.domain.Coordinate;
import com.deltegui.plantio.weather.domain.WeatherReport;
import com.deltegui.plantio.weather.domain.WeatherState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReadReportCaseTest {
    private WeatherProvider weatherProvider;
    private WeatherReportRepository weatherReportRepository;

    private final WeatherReport madrid = new WeatherReport(
            new Coordinate(40.4168, -3.7038),
            "Madrid",
            WeatherState.CLEAR,
            20
    );

    private final WeatherReport algete = new WeatherReport(
            new Coordinate(40.5965, -3.5016),
            "Madrid",
            WeatherState.CLEAR,
            20
    );

    @BeforeEach
    public void initialize() {
        this.weatherProvider = mock(WeatherProvider.class);
        this.weatherReportRepository = mock(WeatherReportRepository.class);
    }

    @Test
    public void shouldAlwaysReturnAWeatherReport() {
        when(weatherReportRepository.find(any())).thenReturn(Optional.of(madrid));
        var readCase = new ReadReportCase(this.weatherProvider, this.weatherReportRepository);
        var result = readCase.handle(new Coordinate(40, -3));
        assertEquals(madrid, result);
    }

    @Test
    public void shouldReturnCachedWithoutMakingARequest() {
        when(weatherReportRepository.find(any())).thenReturn(Optional.of(madrid));
        when(weatherProvider.read(any())).thenReturn(Optional.of(algete));
        var readCase = new ReadReportCase(this.weatherProvider, this.weatherReportRepository);
        var result = readCase.handle(new Coordinate(40, -3));
        assertEquals(result, madrid);
        verify(weatherReportRepository, times(1)).find(any());
        verify(weatherProvider, times(0)).read(any());
    }

    @Test
    public void shouldMakeRequestIfThereIsNoReportCached() {
        when(weatherReportRepository.find(any())).thenReturn(Optional.empty());
        when(weatherProvider.read(any())).thenReturn(Optional.of(algete));
        var readCase = new ReadReportCase(this.weatherProvider, this.weatherReportRepository);
        var result = readCase.handle(new Coordinate(40, -3));
        assertEquals(result, algete);
        verify(weatherReportRepository, times(1)).find(any());
        verify(weatherProvider, times(1)).read(any());
    }

    @Test
    public void shouldMakeRequestIfCachedIsOld() {
        var oldReport = mock(WeatherReport.class);
        when(oldReport.isOld()).thenReturn(true);
        when(weatherReportRepository.find(any())).thenReturn(Optional.of(oldReport));
        when(weatherProvider.read(any())).thenReturn(Optional.of(algete));
        var readCase = new ReadReportCase(this.weatherProvider, this.weatherReportRepository);
        var result = readCase.handle(new Coordinate(40, -3));
        assertEquals(result, algete);
        verify(weatherReportRepository, times(1)).find(any());
        verify(weatherProvider, times(1)).read(any());
    }

    @Test
    public void whenMakeRequestShouldBeCached() {
        when(weatherReportRepository.find(any())).thenReturn(Optional.empty());
        when(weatherProvider.read(any())).thenReturn(Optional.of(algete));
        var readCase = new ReadReportCase(this.weatherProvider, this.weatherReportRepository);
        var result = readCase.handle(new Coordinate(40, -3));
        assertEquals(result, algete);
        verify(weatherReportRepository, times(1)).saveOrReplace(any());
        verify(weatherProvider, times(1)).read(any());
    }

    @Test
    public void shouldThrowErrorIfCannotMakeRequest() {
        when(weatherReportRepository.find(any())).thenReturn(Optional.empty());
        when(weatherProvider.read(any())).thenReturn(Optional.empty());
        var readCase = new ReadReportCase(this.weatherProvider, this.weatherReportRepository);
        var ex = assertThrows(
                DomainException.class,
                () -> readCase.handle(new Coordinate(40, -3))
        );
        assertEquals(WeatherErrors.Read, ex.getError());
    }
}
