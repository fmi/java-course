public interface OldLockable {

    default boolean isLocked() {
        return true;
    }

}
