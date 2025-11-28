package collections.permissions;

import java.util.EnumSet;

public class User {
    private final String username;
    private final EnumSet<Permission> permissions;

    public User(String username, EnumSet<Permission> permissions) {
        this.username = username;
        this.permissions = permissions.clone(); // Defensive copy
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public void grantPermission(Permission permission) {
        permissions.add(permission);
    }

    public void revokePermission(Permission permission) {
        permissions.remove(permission);
    }

    public EnumSet<Permission> getPermissions() {
        return permissions.clone();
    }

    @Override
    public String toString() {
        return username + ": " + permissions;
    }
}
