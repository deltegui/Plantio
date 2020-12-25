package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.users.application.UserRepository;
import com.deltegui.plantio.weather.domain.WeatherReport;
import org.springframework.stereotype.Service;

@Service
public class ReadWeatherAndUpdateLocationCase implements UseCase<ReadAndUpdateRequest, WeatherReport> {
    private final ReadReportCase readReportCase;
    private final UserRepository userRepository;

    public ReadWeatherAndUpdateLocationCase(ReadReportCase readReportCase, UserRepository userRepository) {
        this.readReportCase = readReportCase;
        this.userRepository = userRepository;
    }

    @Override
    public WeatherReport handle(ReadAndUpdateRequest request) throws DomainException {
        this.userRepository.findByName(request.getUsername()).ifPresent((user -> {
            user.setLastPosition(request.getCoordinate());
            this.userRepository.update(user);
        }));
        return this.readReportCase.handle(request.getCoordinate());
    }
}
