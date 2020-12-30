package com.deltegui.plantio.store.domain;

import com.deltegui.plantio.game.domain.PlantType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class StoreItem {
    private final @NotNull PlantType item;
    private @NotNull @Min(1) int amount;
    private final @NotNull @Min(0) double price;

    public StoreItem(PlantType item, int amount, double price) {
        this.item = item;
        this.amount = amount;
        this.price = price;
    }

    public boolean notHaveStock(int amount) {
        return this.amount < amount;
    }

    public void add(int amount) {
        this.amount += amount;
    }

    public void substract(int amount) {
        if (notHaveStock(amount)) {
            return;
        }
        this.amount -= amount;
    }

    public String getItem() {
        return this.item.name();
    }
    public int getAmount() {
        return this.amount;
    }

    public double getPrice() {
        return price;
    }
}
