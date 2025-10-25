package enums.composition;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Demonstrates enum composition and polymorphism in Java.
 * Each enum constant represents a string operation with its own behavior.
 */
public class EnumCompositionExample {

    /**
     * Common interface for all string operations.
     * Promotes polymorphism and abstraction.
     */
    public interface StringOperation {
        String apply(String input);         // Performs the operation
        String getDescription();            // Describes the operation
    }

    /**
     * Basic string operations implemented as enum constants.
     * Each constant overrides the apply method with specific logic.
     */
    public enum BasicStringOperation implements StringOperation {
        STRIP("Removes leading and trailing spaces.") {
            @Override
            public String apply(String input) {
                return input.strip();
            }
        },
        TO_UPPER("Converts all characters to uppercase.") {
            @Override
            public String apply(String input) {
                return input.toUpperCase();
            }
        },
        REVERSE("Reverses the string.") {
            @Override
            public String apply(String input) {
                return new StringBuilder(input).reverse().toString();
            }
        };

        private final String description;

        BasicStringOperation(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    /**
     * Extended string operations like encoding.
     * Demonstrates how enums can encapsulate more complex behavior.
     */
    public enum ExtendedStringOperation implements StringOperation {
        MD5_ENCODE("Encodes the string using MD5.") {
            @Override
            public String apply(String input) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
                    StringBuilder sb = new StringBuilder();
                    for (byte b : digest) {
                        sb.append(String.format("%02x", b));
                    }
                    return sb.toString();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException("MD5 algorithm not available", e);
                }
            }
        },
        BASE64_ENCODE("Encodes the string using Base64.") {
            @Override
            public String apply(String input) {
                return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
            }
        };

        private final String description;

        ExtendedStringOperation(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    /**
     * Demonstrates usage of both basic and extended operations.
     */
    public static void main(String[] args) {
        String input = " Hello World ";

        System.out.println("Original input: \"" + input + "\"\n");

        System.out.println("Applying Basic Operations:");
        for (BasicStringOperation op : BasicStringOperation.values()) {
            String result = op.apply(input);
            System.out.printf("%s → \"%s\"%n", op.getDescription(), result);
        }

        System.out.println("\nApplying Extended Operations:");
        for (ExtendedStringOperation op : ExtendedStringOperation.values()) {
            String result = op.apply(input);
            System.out.printf("%s → \"%s\"%n", op.getDescription(), result);
        }
    }

}
