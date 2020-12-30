package com.deltegui.plantio.store.application;

import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.store.domain.Seeds;

public final class TransactionRequest {
    private final String user;
    private final PlantType type;
    private final int amount;

    public TransactionRequest(String user, PlantType type, int amount) {
        this.user = user;
        this.type = type;
        this.amount = amount;
    }

    public String getUser() {
        return user;
    }

    public Seeds getSeeds() {
        return new Seeds(this.type, this.amount);
    }
}
