package bg.sofia.uni.fmi.mjt.simcity;

import java.util.Map;

public final class Assert {

    private Assert() {
        // Prevent initialization
    }

    public static <T> void notNull(T object) {
        if (object == null) {
            throw new IllegalArgumentException("The provided object cannot be null.");
        }
    }

    public static void notNullOrBlank(String string) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("The provided string cannot be null or blank.");
        }
    }

    public static void notNullOrEmpty(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException("The provided collection cannot be null or empty.");
        }
    }
}
