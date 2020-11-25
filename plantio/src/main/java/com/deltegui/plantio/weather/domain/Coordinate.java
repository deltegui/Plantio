package com.deltegui.plantio.weather.domain;

public class Coordinate {
    private final double latitude;
    private final double longitude;
    private static final double LATITUDE_KILOMETERS_EQUIVALENCE = 110.574;
    private static final double LONGITUDE_KILOMETERS_EQUIVALENCE = 111.320;

    public Coordinate (double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AreaRange calculateAreaRange(double kilometers) {
        return new AreaRange(calculateLatitudeRangeFor(kilometers), calculateLongitudeRangeFor(kilometers));
    }

    public Range calculateLatitudeRangeFor(double kilometers) {
        final double rangeDegrees = kilometers / Coordinate.LATITUDE_KILOMETERS_EQUIVALENCE;
        return new Range(this.latitude, rangeDegrees);
    }

    public Range calculateLongitudeRangeFor(double kilometers) {
        final double correctionFactor = Math.cos(Math.toRadians(this.latitude));
        final double distanceBetweenMeridians = correctionFactor * Coordinate.LONGITUDE_KILOMETERS_EQUIVALENCE;
        final double rangeDegrees = kilometers / distanceBetweenMeridians;
        return new Range(this.longitude, rangeDegrees);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
