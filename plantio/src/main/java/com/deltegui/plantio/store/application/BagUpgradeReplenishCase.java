package com.deltegui.plantio.store.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.store.domain.ItemType;
import com.deltegui.plantio.store.domain.StoreItem;

public class BagUpgradeReplenishCase implements UseCase<Void, Void> {
    private final StoreRepository storeRepository;

    public BagUpgradeReplenishCase(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Void handle(Void request) throws DomainException {
        StoreItem bagUpgradeItem = this.storeRepository.getByItem(ItemType.BAG_UPGRADE);
        bagUpgradeItem.add(ItemType.BAG_UPGRADE.getInitialAmount());
        this.storeRepository.update(bagUpgradeItem);
        return null;
    }
}
