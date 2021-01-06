package com.deltegui.plantio.store.domain;

import com.deltegui.plantio.users.domain.User;

public class PlantApplier implements ItemApplier {
    @Override
    public void applyItem(User user, Order order) {
        user.addToBag(order);
    }

    @Override
    public boolean canApply(User user, Order order) {
        return user.canAddToBag(order);
    }
}
