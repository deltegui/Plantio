package com.deltegui.plantio.users.application;

import com.deltegui.plantio.users.domain.Token;
import com.deltegui.plantio.users.domain.User;

public class SessionResponse {
    private final String name;
    private final double money;
    private final Token token;

    public SessionResponse(User user, Token token) {
        this.name = user.getName();
        this.money = user.getMoney();
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
}
