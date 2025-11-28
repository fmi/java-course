package bg.sofia.uni.fmi.mjt.lockable;

public class DefaultInterfaceExamples {

    public static void main(String... args) {

        Door someDoor = new Door();
        Lockable anotherDoor = new Door();
        Lockable someSafe = new Safe();
        anotherDoor.lock(); // Door locked.
        System.out.println(someSafe.isLocked()); // false
        System.out.println(someDoor.isLocked()); // true

        Object obj = new Safe();

        // classic instanceof: explicit cast is needed in the if body
        if (obj instanceof Lockable) {
            ((Lockable) obj).lock(); // Safe locked.
        }

        // New in Java 17: pattern matching for instanceof
        if (someDoor instanceof Door d) {
            d.lock(); // Door locked.
        }

        Lockable door = Lockable.of("door");
        Lockable safe = Lockable.of("safe");
        Lockable vault = Lockable.of("vault");

        Lockable[] lockables = {door, safe, vault};

        for (Lockable toLock : lockables) {
            toLock.lock();
        }

    }

}
