package bg.sofia.uni.fmi.mjt.smartfridge.storable;

import bg.sofia.uni.fmi.mjt.smartfridge.storable.type.StorableType;

import java.time.LocalDate;

public class Other implements Storable {
    @Override
    public StorableType getType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public LocalDate getExpiration() {
        return null;
    }

    @Override
    public boolean isExpired() {
        throw new UnsupportedOperationException("cannot expire");
    }

}
