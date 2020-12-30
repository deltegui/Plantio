package com.deltegui.plantio.users.application;

import com.deltegui.plantio.users.domain.BagItem;
import com.deltegui.plantio.users.domain.Token;
import com.deltegui.plantio.users.domain.User;

import java.util.List;

public final class SessionResponse {
    private final String name;
    private final double money;
    private final List<BagItem> bag;
    private final Token token;

    public SessionResponse(User user, Token token) {
        this.name = user.getName();
        this.money = user.getMoney();
        this.bag = user.getBag();
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public Token getToken() {
        return token;
    }

    public List<BagItem> getBag() {
        return bag;
    }
}
