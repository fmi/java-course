package bg.sofia.uni.fmi.mjt.lockable;


public class Vault implements Lockable {

    @Override
    public void lock() {
        System.out.println("Vault locked with biometric authentication.");
    }

    @Override
    public boolean isLocked() {
        return true;
    }

}
