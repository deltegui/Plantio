package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.users.application.UserRepository;
import com.deltegui.plantio.users.domain.User;
import com.deltegui.plantio.weather.domain.UserWeatherSnapshot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateWeatherSnapshotCase implements UseCase<Delayer, Void> {
    private final UserRepository userRepository;
    private final WeatherSnapshotRepository snapshotRepository;
    private final ReadReportCase readReportCase;

    public CreateWeatherSnapshotCase(UserRepository userRepository, WeatherSnapshotRepository snapshotRepository, ReadReportCase readReportCase) {
        this.userRepository = userRepository;
        this.snapshotRepository = snapshotRepository;
        this.readReportCase = readReportCase;
    }

    @Override
    public Void handle(Delayer delayer) throws DomainException {
        List<User> users = this.userRepository.getAll();
        for (User user : users) {
            user.getLastPosition().ifPresent(lastPosition -> {
                var report = this.readReportCase.handle(lastPosition);
                this.snapshotRepository.save(new UserWeatherSnapshot(user.getName(), report));
                delayer.delay();
            });
        }
        return null;
    }
}
