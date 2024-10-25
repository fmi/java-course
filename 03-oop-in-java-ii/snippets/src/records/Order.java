package records;

import java.time.LocalDateTime;

public record Order(int id, String username, LocalDateTime timestamp) {
    private static int order_id;

    static {
        order_id = 0;
    }

    public static Order of(String username) {
        return new Order(++order_id, username, LocalDateTime.now());
    }
}