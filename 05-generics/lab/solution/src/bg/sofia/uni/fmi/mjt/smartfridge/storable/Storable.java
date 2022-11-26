package bg.sofia.uni.fmi.mjt.smartfridge.storable;

import bg.sofia.uni.fmi.mjt.smartfridge.storable.type.StorableType;

import java.time.LocalDate;

public interface Storable {

    /**
     * Gets the type of this storable.
     */
    StorableType getType();

    /**
     * Gets the name of this storable.
     */
    String getName();

    /**
     * Gets the expiration date of this storable.
     */
    LocalDate getExpiration();

    /**
     * Returns true, if the storable is expired.
     */
    boolean isExpired();

}
