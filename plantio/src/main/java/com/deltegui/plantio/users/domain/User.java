package com.deltegui.plantio.users.domain;

import com.deltegui.plantio.store.domain.Order;
import com.deltegui.plantio.weather.domain.Coordinate;

import java.util.*;

public class User {
    private final String name;
    private final String password;
    private Coordinate lastPosition;
    private double money;
    private List<BagItem> bag;

    public User(String name, String password, Coordinate lastPosition, double money) {
        this.name = name;
        this.password = password;
        this.lastPosition = lastPosition;
        this.money = money;
        this.bag = new ArrayList<>();
    }

    public User(String name, String password, Coordinate lastPosition) {
        this(name, password, lastPosition, 0);
    }

    public User(String name, String password, double money) {
        this(name, password, null, money);
    }

    public User(String name, String password) {
        this(name, password, null, 0);
    }

    public void payFor(Order order) {
        if (! this.canPay(order)) {
            return;
        }
        this.money -= order.getTotalPrice();
        getItemFromBag(order.getItem()).ifPresentOrElse(
                item -> item.add(order.getAmount()),
                () -> bag.add(order.toBagItem())
        );
    }

    public boolean canPay(Order order) {
        return this.money >= order.getTotalPrice();
    }

    public boolean canSell(Order order) {
        var optionalItem = this.getItemFromBag(order.getItem());
        if (optionalItem.isEmpty()) {
            return false;
        }
        return optionalItem.get().canSubstract(order.getAmount());
    }

    public void sell(Order order) {
        if (! canSell(order)) {
            return;
        }
        this.getItemFromBag(order.getItem()).ifPresent((BagItem item) -> {
            item.substract(order.getAmount());
            if (item.amountIsZero()) {
                this.bag.remove(item);
            }
            this.depositMoney(order.getTotalPrice());
        });
    }

    private Optional<BagItem> getItemFromBag(String item) {
        for (BagItem seeds : this.bag) {
            if (seeds.getItem().equals(item)) {
                return Optional.of(seeds);
            }
        }
        return Optional.empty();
    }

    public void depositMoney(double income) {
        this.money += income;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Optional<Coordinate> getLastPosition() {
        return this.lastPosition == null ? Optional.empty() : Optional.of(this.lastPosition);
    }

    public void setLastPosition(Coordinate updatedPosition) {
        this.lastPosition = updatedPosition;
    }

    public double getMoney() {
        return money;
    }

    public List<BagItem> getBag() {
        return bag;
    }

    public void setBag(List<BagItem> bag) {
        this.bag = bag;
    }
}
