package com.deltegui.plantio.weather.implementation;

import com.deltegui.plantio.weather.application.WeatherProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class InjectionConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public WeatherProvider createOpenWeatherProvider() {
        return new OpenWeatherMapProvider(environment.getProperty("openweather"));
    }
}
