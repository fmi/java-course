package bg.sofia.uni.fmi.mjt.shopping.item;

import java.util.Objects;

public class Chocolate implements Item {

    private String id;

    public Chocolate(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chocolate chocolate = (Chocolate) o;
        return Objects.equals(id, chocolate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
