package bg.sofia.uni.fmi.mjt.smartfridge.storable;

import java.util.Comparator;

public class StorableByExpirationComparator implements Comparator<Storable> {

    @Override
    public int compare(Storable o1, Storable o2) {
        return o1.getExpiration().compareTo(o2.getExpiration());
    }

}
