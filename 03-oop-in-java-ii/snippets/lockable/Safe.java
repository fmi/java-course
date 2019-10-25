public class Safe implements Lockable, OldLockable {

    @Override
    public void lock() {
        System.out.println("Safe locked.");
    }

    @Override
    public boolean isLocked() {
        return Lockable.super.isLocked();
    }

}
