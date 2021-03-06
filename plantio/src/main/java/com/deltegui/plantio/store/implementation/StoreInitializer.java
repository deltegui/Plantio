package com.deltegui.plantio.store.implementation;

import com.deltegui.plantio.store.application.StoreRepository;
import com.deltegui.plantio.store.domain.ItemType;
import com.deltegui.plantio.store.domain.StoreItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class StoreInitializer {
    private final StoreRepository storeRepository;
    private final Logger logger = LoggerFactory.getLogger(StoreInitializer.class);
    private List<StoreItem> existingItems;

    public StoreInitializer(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public static StoreInitializer fromContext(ConfigurableApplicationContext ctx) {
        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        return new StoreInitializer(new MysqlStoreRepository(jdbcTemplate));
    }

    public void ensureThereAreItemsInStore() {
        this.existingItems = storeRepository.getAll();
        ItemType[] availableTypes = ItemType.values();
        for (ItemType type : availableTypes) {
            createIfNotExists(type);
        }
    }

    private void createIfNotExists(ItemType type) {
        existingItems
                .stream()
                .filter(item -> item.isOfType(type))
                .findFirst()
                .or(() -> this.createDefaultItem(type));
    }

    private Optional<StoreItem> createDefaultItem(ItemType type) {
        logger.info("Creating store item for " + type.name());
        var storeItem = StoreItem.createDefault(type);
        storeRepository.add(storeItem);
        return Optional.of(storeItem);
    }
}
