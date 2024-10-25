package enums.composition;

public class Example {
    public interface StringOperation {
        String getDescription();
    }

    public enum BasicStringOperation implements StringOperation {
        TRIM("Removing leading and trailing spaces."),
        TO_UPPER("Changing all characters into upper case."),
        REVERSE("Reversing the given string.");

        private final String description;

        BasicStringOperation(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }

        // constructor and getter override
    }

    public enum ExtendedStringOperation implements StringOperation {
        MD5_ENCODE("Encoding the given string using the MD5 algorithm."),
        BASE64_ENCODE("Encoding the given string using the BASE64 algorithm.");

        private final String description;

        ExtendedStringOperation(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    public static void main(String[] args) {
        StringOperation trim = BasicStringOperation.TRIM;
        StringOperation md5 = ExtendedStringOperation.MD5_ENCODE;

        System.out.println(trim.getDescription());
        System.out.println(md5.getDescription());
    }
}
