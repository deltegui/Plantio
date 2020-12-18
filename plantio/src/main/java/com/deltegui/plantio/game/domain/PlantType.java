package com.deltegui.plantio.game.domain;

public enum PlantType {
    WHEAT, CACTUS;

    public static PlantType fromString(String type) {
        switch (type) {
            case "CACTUS": return PlantType.CACTUS;
            default: return PlantType.WHEAT;
        }
    }
}
