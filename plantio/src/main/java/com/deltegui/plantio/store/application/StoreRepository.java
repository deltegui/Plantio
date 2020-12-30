package com.deltegui.plantio.store.application;

import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.store.domain.StoreItem;

import java.util.List;

public interface StoreRepository {
    List<StoreItem> getAll();
    StoreItem getByItem(PlantType itemType);
    void update(StoreItem item);
    void add(StoreItem item);
}
