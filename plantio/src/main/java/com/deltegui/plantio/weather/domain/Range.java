package com.deltegui.plantio.weather.domain;

public class Range {
    private final double center;
    private final double range;

    public Range(double center, double range) {
        this.center = center;
        this.range = range;
    }

    public boolean includes(Range other) {
        return other.getMinimum() >= this.getMinimum() && other.getMinimum() <= this.getMaximum() ||
                other.getMaximum() >= this.getMinimum() && other.getMaximum() <= this.getMaximum() ||
                other.getMaximum() > this.getMaximum() && other.getMinimum() < this.getMinimum();
    }

    public double getMaximum() {
        return this.center + this.range;
    }

    public double getMinimum() {
        return this.center - this.range;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Range otherRange = (Range)object;
        return Double.compare(otherRange.center, this.center) == 0 &&
                Double.compare(otherRange.range, this.range) == 0;
    }
}
