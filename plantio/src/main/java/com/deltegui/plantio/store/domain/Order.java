package com.deltegui.plantio.store.domain;

import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.users.domain.BagItem;

public class Order {
    private final StoreItem storeItem;
    private final int amount;

    public Order(StoreItem storeItem, int amount) {
        this.storeItem = storeItem;
        this.amount = amount;
    }

    public BagItem toBagItem() {
        return new BagItem(PlantType.fromString(this.storeItem.getName()), this.amount);
    }

    public double getTotalPrice() {
        return storeItem.getPrice() * amount;
    }

    public String getItem() {
        return this.storeItem.getName();
    }

    public int getAmount() {
        return amount;
    }
}
