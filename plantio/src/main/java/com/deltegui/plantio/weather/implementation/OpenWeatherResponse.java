package com.deltegui.plantio.weather.implementation;

import com.deltegui.plantio.weather.domain.WeatherState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenWeatherResponse {
    private final Map<String, Object> root;

    private OpenWeatherResponse(Map<String, Object> root) {
        this.root = root;
    }

    public static OpenWeatherResponse fromHttpResponse(HttpResponse<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(response.body(), Map.class);
        return new OpenWeatherResponse(map);
    }

    public double readTemperature() {
        final double KELVIN_EQ = 273.15;
        var main = (Map<String, Object>)root.get("main");
        if (main == null) {
            return 0;
        }
        Double temp = (Double)main.get("temp");
        if (temp == null) {
            return 0;
        }
        return round(temp - KELVIN_EQ, 1);
    }

    private double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public WeatherState readWeatherState() {
        var weatherList = (List<Object>)root.get("weather");
        if (weatherList == null) {
            return WeatherState.Clear;
        }
        var weatherData = readFirstElementOrDefault(weatherList);
        String value = (String)weatherData.get("main");
        if (value == null) {
            return WeatherState.Clear;
        }
        return WeatherState.fromString(value);
    }

    private Map<String, Object> readFirstElementOrDefault(List<Object> list) {
        try {
            return (Map<String, Object>)list.get(0);
        } catch (IndexOutOfBoundsException ex) {
            return new HashMap<>();
        }
    }

    public String readLocation() {
        String location = (String)root.get("name");
        return location == null ? "" : location;
    }
}
