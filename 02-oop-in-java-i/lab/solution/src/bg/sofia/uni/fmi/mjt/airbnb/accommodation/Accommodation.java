package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

import java.util.Objects;

public abstract class Accommodation {

    private static final int ID_PREFIX_LENGTH = 3;
    private final String id;
    private final String type;
    private final Location location;

    public Accommodation(String type, Location location) {
        this.type = type;
        this.location = location;
        this.id = generateId();
    }

    private String generateId() {
        long id_suffix = getIdSuffix();
        String prefix = type.length() >= ID_PREFIX_LENGTH ? type.substring(0, ID_PREFIX_LENGTH) : type;
        return String.format("%s-%d", prefix.toUpperCase(), id_suffix);
    }

    protected abstract long getIdSuffix();

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accommodation that = (Accommodation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", id, type, location);
    }

}
