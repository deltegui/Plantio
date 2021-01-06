package com.deltegui.plantio.store.application;

import com.deltegui.plantio.store.domain.ItemType;
import com.deltegui.plantio.store.domain.TransactionItem;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public final class TransactionRequest {
    private final @NotNull String user;
    private final @NotNull ItemType type;
    private final @Min(1) int amount;

    public TransactionRequest(String user, ItemType type, int amount) {
        this.user = user;
        this.type = type;
        this.amount = amount;
    }

    public String getUser() {
        return user;
    }

    public TransactionItem getSeeds() {
        return new TransactionItem(this.type, this.amount);
    }
}
