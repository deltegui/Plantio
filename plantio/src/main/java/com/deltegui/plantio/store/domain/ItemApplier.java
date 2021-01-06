package com.deltegui.plantio.store.domain;

import com.deltegui.plantio.users.domain.User;

public interface ItemApplier {
    ItemApplier PLANT = new PlantApplier();
    ItemApplier BAG_UPGRADE = new BagUpgradeApplier();

    void applyItem(User user, Order order);
    boolean canApply(User user, Order order);
}
