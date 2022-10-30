package bg.sofia.uni.fmi.mjt.airbnb.accommodation.location;

import java.util.Objects;

public class Location {

    private final double x;
    private final double y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(Location other) {
        double deltaA = other.x - x;
        double deltaB = other.y - y;

        return Math.sqrt(Math.pow(deltaA, 2) + Math.pow(deltaB, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("Location: (x = %.2f , y = %.2f)", x, y);
    }

}
