package bg.sofia.uni.fmi.mjt.shopping.item;

import java.util.Objects;

public abstract class BaseItem implements Item {

    private String id;

    public BaseItem(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseItem other = (BaseItem) obj;
        return Objects.equals(id, other.id);
    }
}
