package com.deltegui.plantio.store.implementation;

import com.deltegui.plantio.store.application.AdjustPriceCase;
import com.deltegui.plantio.weather.implementation.ScheduledWeatherSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledPriceAdjustement {
    private final AdjustPriceCase adjustPriceCase;
    private final Logger logger = LoggerFactory.getLogger(ScheduledWeatherSnapshot.class);

    public ScheduledPriceAdjustement(AdjustPriceCase adjustPriceCase) {
        this.adjustPriceCase = adjustPriceCase;
    }

    @Scheduled(cron = "0 0/15 * * * ?")
    public void adjustStorePrices() {
        logger.info("Starting to adjust prices...");
        adjustPriceCase.handle(null);
        logger.info("Done adjusting prices!");
    }
}
