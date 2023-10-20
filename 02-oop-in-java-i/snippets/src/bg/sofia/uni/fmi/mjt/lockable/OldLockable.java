package bg.sofia.uni.fmi.mjt.lockable;

public interface OldLockable {

    default boolean isLocked() {
        return true;
    }

}
