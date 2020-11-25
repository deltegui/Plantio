package com.deltegui.plantio.weather.domain;

public class AreaRange {
    private final Range latitudeRange;
    private final Range longitudeRange;

    public AreaRange(Range latitude, Range longitude) {
        this.latitudeRange = latitude;
        this.longitudeRange = longitude;
    }

    public boolean includes(AreaRange other) {
        return this.longitudeRange.includes(other.longitudeRange) &&
                this.latitudeRange.includes(other.latitudeRange);
    }
}
