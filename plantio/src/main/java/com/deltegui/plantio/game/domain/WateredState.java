package com.deltegui.plantio.game.domain;

public enum WateredState {
    DRY, WET;
    public static WateredState fromString(String str) {
        return str.equals("WET") ? WateredState.WET : WateredState.DRY;
    }
}
