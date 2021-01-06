package com.deltegui.plantio.store.domain;

import com.deltegui.plantio.users.domain.User;

public final class TransactionItem {
    private final ItemType type;
    private final int amount;

    public TransactionItem(ItemType plantType, int amount) {
        this.type = plantType;
        this.amount = amount;
    }

    public ItemType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public boolean canBeAppliedTo(User user, Order order) {
        return this.type.canBeAppliedTo(user, order);
    }
}
