package bg.sofia.uni.fmi.mjt.lockable;

public class Safe implements Lockable, OldLockable {

    @Override
    public void lock() {
        System.out.println("Safe locked.");
    }

    @Override
    public boolean isLocked() {
        // We will get a compile-time error, if we don't override the isLocked() method here:
        // - "Safe inherits unrelated defaults for isLocked() from types Lockable and OldLockable"
        return Lockable.super.isLocked();
    }

}
