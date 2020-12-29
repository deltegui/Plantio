package com.deltegui.plantio.users.application;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public final class SessionRequest {
    @Length(max = 255, min = 2)
    @NotNull
    private final String name;

    @Length(max = 255, min = 3)
    @NotNull
    private final String password;

    public SessionRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
