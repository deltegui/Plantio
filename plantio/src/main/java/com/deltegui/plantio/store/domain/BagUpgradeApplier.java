package com.deltegui.plantio.store.domain;

import com.deltegui.plantio.users.domain.User;

public class BagUpgradeApplier implements ItemApplier {
    @Override
    public void applyItem(User user, Order order) {
        user.incrementBagSize(order.getAmount());
    }

    @Override
    public boolean canApply(User user, Order order) {
        return user.canIncrementBagSize(order.getAmount());
    }
}
