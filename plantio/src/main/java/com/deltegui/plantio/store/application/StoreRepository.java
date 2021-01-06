package com.deltegui.plantio.store.application;

import com.deltegui.plantio.store.domain.ItemType;
import com.deltegui.plantio.store.domain.StoreItem;

import java.util.List;

public interface StoreRepository {
    List<StoreItem> getAll();
    StoreItem getByItem(ItemType itemType);
    void update(StoreItem item);
    void add(StoreItem item);
}
