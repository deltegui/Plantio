package com.deltegui.plantio.weather.implementation;

import com.deltegui.plantio.weather.application.WeatherProvider;
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
    public Optional<WeatherReport> read(String location) {
        try {
            return Optional.of(makeRequest(location));
        } catch (IOException | InterruptedException exception) {
            return Optional.empty();
        }
    }

    private WeatherReport makeRequest(String location) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(createUrl(location)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var data = OpenWeatherResponse.fromHttpResponse(response);
        return new WeatherReport(location, data.readWeatherState(), data.readTemperature());
    }

    private URI createUrl(String location) {
        var url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", location, weatherKey);
        return URI.create(url);
    }
}
