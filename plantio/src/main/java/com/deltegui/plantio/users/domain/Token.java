package com.deltegui.plantio.users.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Token {
    private final String value;
    private final LocalDateTime expiration;
    private final String user;

    public Token(String value, LocalDateTime expiration, String user) {
        this.value = value;
        this.expiration = expiration;
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    @JsonIgnore
    public String getUser() {
        return user;
    }
}
