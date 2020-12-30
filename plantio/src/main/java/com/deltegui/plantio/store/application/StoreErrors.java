package com.deltegui.plantio.store.application;

import com.deltegui.plantio.common.DomainError;

public enum StoreErrors implements DomainError {
    UNAFFORDABLE(300, "You cant afford the item"),
    OUT_OF_STOCK(301, "Out of stock"),
    NOT_ENOUGH_ELEMENTS_TO_SELL(302, "Not enough element to sell");
    private final int code;
    private final String message;

    StoreErrors(int code, String message) {
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
