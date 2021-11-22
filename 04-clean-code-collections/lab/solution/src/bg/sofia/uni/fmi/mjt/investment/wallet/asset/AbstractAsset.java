package bg.sofia.uni.fmi.mjt.investment.wallet.asset;

import java.util.Objects;

public abstract class AbstractAsset implements Asset {

    private final String id;
    private final String name;
    private final AssetType type;

    public AbstractAsset(String id, String name, AssetType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AssetType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractAsset other = (AbstractAsset) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name) && type == other.type;
    }
}
