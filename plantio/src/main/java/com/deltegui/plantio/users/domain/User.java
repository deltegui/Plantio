package com.deltegui.plantio.users.domain;

import com.deltegui.plantio.weather.domain.Coordinate;

import java.util.Optional;

public class User {
    private final String name;
    private final String password;
    private Coordinate lastPosition;
    private double money;

    public User(String name, String password, Coordinate lastPosition, double money) {
        this.name = name;
        this.password = password;
        this.lastPosition = lastPosition;
        this.money = money;
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

    public boolean canPay(Payable payable) {
        return this.money >= payable.getCost();
    }

    public void payFor(Payable payable) {
        if (this.canPay(payable)) {
            this.money -= payable.getCost();
        }
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
}
