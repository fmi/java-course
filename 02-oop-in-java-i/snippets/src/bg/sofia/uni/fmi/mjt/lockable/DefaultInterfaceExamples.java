package bg.sofia.uni.fmi.mjt.lockable;

public class DefaultInterfaceExamples {

    public static void main(String... args) {

        Door door = new Door();
        Lockable anotherDoor = new Door();
        Lockable safe = new Safe();
        anotherDoor.lock(); // Door locked.
        System.out.println(safe.isLocked()); // false
        System.out.println(door.isLocked()); // true

        Object obj = new Safe();
        Lockable lockable = Lockable.getInstance(true);

        // classic instanceof: explicit cast is needed in the if body
        if (obj instanceof Lockable) {
            ((Lockable) obj).lock(); // Safe locked.
        }

        // New in Java 17: pattern matching for instanceof
        if (lockable instanceof Door d) {
            d.lock(); // Door locked.
        }

    }

}
