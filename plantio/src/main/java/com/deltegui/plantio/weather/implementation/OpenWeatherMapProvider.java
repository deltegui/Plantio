package com.deltegui.plantio.weather.implementation;

import com.deltegui.plantio.weather.application.WeatherProvider;
import com.deltegui.plantio.weather.domain.Coordinate;
import com.deltegui.plantio.weather.domain.WeatherReport;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class OpenWeatherMapProvider implements WeatherProvider {
    private final String weatherKey;
    private final HttpClient client;

    public OpenWeatherMapProvider(String weatherKey) {
        this.weatherKey = weatherKey;
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public Optional<WeatherReport> read(Coordinate coordinate) {
        try {
            return Optional.of(makeRequest(coordinate));
        } catch (IOException | InterruptedException exception) {
            return Optional.empty();
        }
    }

    private WeatherReport makeRequest(Coordinate coordinate) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(createUrl(coordinate)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var data = OpenWeatherResponse.fromHttpResponse(response);
        return new WeatherReport(
                coordinate,
                data.readLocation(),
                data.readWeatherState(),
                data.readTemperature(),
                data.readSunrise(),
                data.readSunset());
    }

    private URI createUrl(Coordinate coord) {
        var url = String.format(
                "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s",
                coord.getLatitude(),
                coord.getLongitude(),
                weatherKey);
        return URI.create(url);
    }
}
