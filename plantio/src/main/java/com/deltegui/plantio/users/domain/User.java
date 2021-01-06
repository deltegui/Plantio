package com.deltegui.plantio.users.domain;

import com.deltegui.plantio.store.domain.Order;
import com.deltegui.plantio.weather.domain.Coordinate;

import java.util.*;

public class User {
    private static final double DEFAULT_MONEY = 10;
    private static final int INITIAL_BAG_SIZE = 10;
    private static final int MAX_BAG_SIZE = 70;

    private final String name;
    private final String password;
    private Coordinate lastPosition;
    private double money;
    private int bagSize;
    private List<BagItem> bag;

    public User(String name, String password, Coordinate lastPosition, double money, int bagSize) {
        this.name = name;
        this.password = password;
        this.lastPosition = lastPosition;
        this.money = money;
        this.bag = new ArrayList<>();
        this.bagSize = bagSize;
    }

    public User(String name, String password, Coordinate lastPosition) {
        this(name, password, lastPosition, DEFAULT_MONEY, INITIAL_BAG_SIZE);
    }

    public User(String name, String password, double money) {
        this(name, password, null, money, INITIAL_BAG_SIZE);
    }

    public User(String name, String password) {
        this(name, password, null, DEFAULT_MONEY, INITIAL_BAG_SIZE);
    }

    public void payFor(Order order) {
        if (! this.canPay(order)) {
            return;
        }
        this.money = roundAvoid(this.money - order.getTotalPrice(), 1);
        order.applyTo(this);
    }

    private double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public void addToBag(Order order) {
        getItemFromBag(order.getItem()).ifPresentOrElse(
                item -> item.add(order.getAmount()),
                () -> bag.add(order.toBagItem())
        );
    }

    public boolean canAddToBag(Order order) {
        List<BagItem> before = new ArrayList<>(this.bag);
        this.addToBag(order);
        boolean result = this.isValidBagSize(this.bag);
        this.bag = before;
        return result;
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

    public boolean canIncrementBagSize(int times) {
        return (this.bagSize + times) <= MAX_BAG_SIZE;
    }

    public void incrementBagSize(int times) {
        if (canIncrementBagSize(times)) {
            this.bagSize+=times;
        }
    }

    public boolean isValidBagSize(List<BagItem> bag) {
        final int items = bag
                .stream()
                .map(BagItem::getAmount)
                .reduce(Integer::sum)
                .orElse(0);
        return items <= this.bagSize;
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
        if (bag.size() >= this.bagSize) {
            return;
        }
        this.bag = bag;
    }

    public int getBagSize() {
        return bagSize;
    }
}
