package com.deltegui.plantio.store.domain;

public class Order {
    private final StoreItem storeItem;
    private final int amount;

    public Order(StoreItem storeItem, int amount) {
        this.storeItem = storeItem;
        this.amount = amount;
    }

    public double getTotalPrice() {
        return storeItem.getPrice() * amount;
    }

    public String getItem() {
        return this.storeItem.getItem();
    }

    public int getAmount() {
        return amount;
    }
}
