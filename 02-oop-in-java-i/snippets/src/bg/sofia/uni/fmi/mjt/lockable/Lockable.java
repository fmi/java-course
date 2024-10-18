package bg.sofia.uni.fmi.mjt.lockable;

public interface Lockable {

    void lock();

    default boolean isLocked() {
        return false;
    }

    // Static factory method: typical use of static methods in interfaces.
    // Keep calm - will learn what Factory is in the Design Patterns lecture
    static Lockable getInstance(boolean isDoor) {
        if (isDoor) {
            return new Door();
        } else {
            return new Safe();
        }
    }

}
