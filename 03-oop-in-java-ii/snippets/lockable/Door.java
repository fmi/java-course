public class Door implements Lockable, OldLockable {

    @Override
    public void lock() {
        System.out.println("Door locked.");
    }

    @Override
    public boolean isLocked() {
        return true;
    }

}
