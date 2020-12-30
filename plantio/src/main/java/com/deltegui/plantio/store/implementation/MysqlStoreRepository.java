package com.deltegui.plantio.store.implementation;

import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.store.application.StoreRepository;
import com.deltegui.plantio.store.domain.StoreItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MysqlStoreRepository implements StoreRepository {
    @Override
    public List<StoreItem> getAll() {
        return new ArrayList<>();
    }

    @Override
    public StoreItem getByItem(PlantType item) {
        return new StoreItem(PlantType.CACTUS, 1, 22);
    }

    @Override
    public void update(StoreItem seeds) {

    }
}
