package com.deltegui.plantio.game.application;

public final class LoadRequest {
    private final String user;

    public LoadRequest(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }
}
