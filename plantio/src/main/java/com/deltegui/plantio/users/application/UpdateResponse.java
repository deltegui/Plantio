package com.deltegui.plantio.users.application;

import com.deltegui.plantio.users.domain.BagItem;
import com.deltegui.plantio.users.domain.User;

import java.util.List;

public final class UpdateResponse {
    private final String name;
    private final double money;
    private final List<BagItem> bag;

    public UpdateResponse(User user) {
        this.name = user.getName();
        this.money = user.getMoney();
        this.bag = user.getBag();
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public List<BagItem> getBag() {
        return bag;
    }
}
