package com.deltegui.plantio.users.application;

import com.deltegui.plantio.common.DomainError;

public enum UserErrors implements DomainError {
    NotFound(100, "User not found"),
    AlreadyExsists(101, "User already exists"),
    InvalidPassword(102, "Invalid password"),
    BagOverflow(103, "Too many items in bag");
    private final int code;
    private final String message;

    UserErrors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
