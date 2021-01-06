package com.deltegui.plantio.store.implementation;

import com.deltegui.plantio.store.application.BagUpgradeReplenishCase;
import com.deltegui.plantio.weather.implementation.ScheduledWeatherSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class ScheduledBagReplenish {
    private final BagUpgradeReplenishCase bagUpgradeReplenish;
    private final Logger logger = LoggerFactory.getLogger(ScheduledWeatherSnapshot.class);

    public ScheduledBagReplenish(BagUpgradeReplenishCase bagUpgradeReplenish) {
        this.bagUpgradeReplenish = bagUpgradeReplenish;
    }

    @Scheduled(cron = "0 0 1 * * MON")
    public void adjustStorePrices() {
        logger.info("Replenishing bag upgrades...");
        bagUpgradeReplenish.handle(null);
        logger.info("Done replenishing bag upgrades!");
    }
}
