package collections.permissions;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

public class PermissionTracker {
    private final EnumMap<Permission, Set<User>> usersByPermission = new EnumMap<>(Permission.class);

    public PermissionTracker() {
        for (Permission p : Permission.values()) {
            usersByPermission.put(p, new HashSet<>());
        }
    }

    public void registerUser(User user) {
        for (Permission p : user.getPermissions()) {
            usersByPermission.get(p).add(user);
        }
    }

    public Set<User> getUsersWithPermission(Permission permission) {
        return usersByPermission.get(permission);
    }
}
