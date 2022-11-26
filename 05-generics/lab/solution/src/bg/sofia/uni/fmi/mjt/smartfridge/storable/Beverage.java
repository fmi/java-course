package bg.sofia.uni.fmi.mjt.smartfridge.storable;

import bg.sofia.uni.fmi.mjt.smartfridge.storable.type.StorableType;

import java.time.LocalDate;

public class Beverage extends AbstractStorable {
    public Beverage(String name, LocalDate expirationDate) {
        super(name, expirationDate);
    }

    @Override
    public StorableType getType() {
        return StorableType.BEVERAGE;
    }

}
