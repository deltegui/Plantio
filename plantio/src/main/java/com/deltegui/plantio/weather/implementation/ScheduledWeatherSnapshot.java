package com.deltegui.plantio.weather.implementation;

import com.deltegui.plantio.weather.application.CreateWeatherSnapshotCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledWeatherSnapshot {
    private final CreateWeatherSnapshotCase createWeatherSnapshotCase;
    private static final long DELAY_MILLISECONDS = 120_000;
    private final Logger logger = LoggerFactory.getLogger(ScheduledWeatherSnapshot.class);

    public ScheduledWeatherSnapshot(CreateWeatherSnapshotCase createWeatherSnapshotCase) {
        this.createWeatherSnapshotCase = createWeatherSnapshotCase;
    }

    @Scheduled(cron = "0 0 */2 * * ?")
    public void createWeatherSnapshot() {
        logger.info("Starting to read weather snapshots");
        this.createWeatherSnapshotCase.handle(() -> {
            try {
                logger.info("Waiting for cooldown...");
                Thread.sleep(DELAY_MILLISECONDS);
            } catch(InterruptedException ex) {
                throw new RuntimeException("Failed running delayer: " + ex.getMessage());
            }
        });
        logger.info("Done reading snapshots!");
    }
}
