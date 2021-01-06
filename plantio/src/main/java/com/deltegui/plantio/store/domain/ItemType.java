package com.deltegui.plantio.store.domain;

import com.deltegui.plantio.users.domain.User;

public enum ItemType {
    WHEAT(2, 50, ItemApplier.PLANT),
    CACTUS(2, 50, ItemApplier.PLANT),
    LAVENDER(2, 50, ItemApplier.PLANT),
    BAG_UPGRADE(5, 5, ItemApplier.BAG_UPGRADE);
    private final double basePrice;
    private final int initialAmount;
    private final ItemApplier applier;

    ItemType(double basePrice, int initialAmount, ItemApplier itemApplier) {
        this.basePrice = basePrice;
        this.initialAmount = initialAmount;
        this.applier = itemApplier;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getInitialAmount() {
        return initialAmount;
    }

    public void apply(User user, Order order) {
        this.applier.applyItem(user, order);
    }

    public boolean canBeAppliedTo(User user, Order order) {
        return this.applier.canApply(user, order);
    }
}
