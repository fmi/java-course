package collections.permissions;

import java.util.EnumSet;

public class EnumCollectionsDemo {
    public static void main(String[] args) {
        User alice = new User("Alice", EnumSet.of(Permission.READ, Permission.WRITE));
        User bob = new User("Bob", EnumSet.of(Permission.READ, Permission.EXECUTE));
        User carol = new User("Carol", EnumSet.allOf(Permission.class));

        PermissionTracker tracker = new PermissionTracker();
        tracker.registerUser(alice);
        tracker.registerUser(bob);
        tracker.registerUser(carol);

        System.out.println("Users with READ permission: " + tracker.getUsersWithPermission(Permission.READ));
        System.out.println("Users with EXECUTE permission: " + tracker.getUsersWithPermission(Permission.EXECUTE));
        System.out.println("Users with DELETE permission: " + tracker.getUsersWithPermission(Permission.DELETE));
    }
}
