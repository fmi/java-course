package bg.sofia.uni.fmi.mjt.lockable;

public interface Lockable {

    void lock();

    default boolean isLocked() {
        return false;
    }

    // Static factory method: typical use of static methods in interfaces.
    // Keep calm - will learn what Factory is in the Design Patterns lecture
    static Lockable of(String type) {
        switch (type.toLowerCase()) {
            case "door":
                return new Door();
            case "safe":
                return new Safe();
            case "vault":
                return new Vault();
            default:
                return null;
        }
    }

}
