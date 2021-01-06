package com.deltegui.plantio.store.domain;

import com.deltegui.plantio.users.domain.User;

public class StoreItem {
    private final ItemType item;
    private int amount;
    private int oldAmount;
    private double price;

    public StoreItem(ItemType item, int amount, int oldAmount, double price) {
        this.item = item;
        this.amount = amount;
        this.oldAmount = oldAmount;
        this.price = price;
    }

    public static StoreItem createDefault(ItemType type) {
        final int initialAmount = type.getInitialAmount();
        final double basePrice = type.getBasePrice();
        return new StoreItem(type, initialAmount, initialAmount, basePrice);
    }

    public boolean isOfType(ItemType type) {
        return this.item == type;
    }

    public void apply(User user, Order order) {
        this.item.apply(user, order);
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
        final double basePrice = this.item.getBasePrice();
        this.price += adjustment;
        if (this.price < basePrice) {
            this.price = basePrice;
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
