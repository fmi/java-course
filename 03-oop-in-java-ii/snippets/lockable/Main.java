public class Main {

    public static void main(String... args) {
        Door door = new Door();
        Lockable anotherDoor = new Door();
        Lockable safe = new Safe();
        anotherDoor.lock(); // Door locked.
        System.out.println(safe.isLocked()); // false
        System.out.println(door.isLocked()); // true

        Lockable lockable = Lockable.getInstance(true);
        System.out.println(lockable instanceof Door); // true
    }

}
