package com.deltegui.plantio.users.application;

import com.deltegui.plantio.users.domain.Token;

public class SessionResponse {
    private final String name;
    private final Token token;

    public SessionResponse(String name, Token token) {
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public Token getToken() {
        return token;
    }
}
