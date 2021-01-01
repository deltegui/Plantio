package com.deltegui.plantio.store.domain;

import com.deltegui.plantio.game.domain.PlantType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class StoreItem {
    private final static double BASE_PRICE = 2;
    private final static int INITIAL_AMOUNT = 50;

    private final @NotNull PlantType item;
    private @NotNull @Min(1) int amount;
    private int oldAmount;
    private @NotNull @Min(0) double price;

    public StoreItem(PlantType item, int amount, int oldAmount, double price) {
        this.item = item;
        this.amount = amount;
        this.oldAmount = oldAmount;
        this.price = price;
    }

    public static StoreItem createDefault(PlantType type) {
        return new StoreItem(type, INITIAL_AMOUNT, INITIAL_AMOUNT, BASE_PRICE);
    }

    public boolean isOfType(PlantType type) {
        return this.item == type;
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

    public void adjustPrice() {
        final double adjustment = (this.oldAmount - this.amount) * 0.1;
        this.price += adjustment;
        if (this.price < BASE_PRICE) {
            this.price = BASE_PRICE;
        }
        this.oldAmount = this.amount;
    }

    public String getName() {
        return this.item.name();
    }

    public int getAmount() {
        return this.amount;
    }

    public int getOldAmount() {
        return oldAmount;
    }

    public double getPrice() {
        return price;
    }
}
