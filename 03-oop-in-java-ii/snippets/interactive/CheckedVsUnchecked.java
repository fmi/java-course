/**
 * ============================================================================
 *  LAB 03 · INTERACTIVE SNIPPET
 * ============================================================================
 *
 *    1. For each call in main(), PREDICT: compiles? / checked or unchecked?
 *    2. Two lines are commented out ON PURPOSE. PREDICT why, then uncomment
 *       them one at a time and read the compiler error.
 * ============================================================================
 */
import java.io.IOException;

public class CheckedVsUnchecked {

    static String readConfig(String path) throws IOException {
        if (path.isBlank()) {
            throw new IOException("no config file at: " + path);
        }
        return "port=8080";
    }

    static int portFromLine(String line) {
        return Integer.parseInt(line.split("=")[1]);
    }

    public static void main(String... args) {
        try {
            System.out.println(readConfig("app.conf"));   // PREDICT: prints ______
        } catch (IOException e) {
            System.out.println("handled checked: " + e.getMessage());
        }

        try {
            readConfig("");                               // PREDICT: which branch? ______
        } catch (IOException e) {
            System.out.println("handled checked: " + e.getMessage());  // PREDICT: ______
        }

        System.out.println("port = " + portFromLine("port=8080"));     // PREDICT: ______

        System.out.println("port = " + portFromLine("port=garbage"));  // PREDICT: ______

        System.out.println("this line is never reached"); // PREDICT: printed? ______

        // ---- Uncomment ONE at a time and read the COMPILER error ----

        // readConfig("app.conf");

        // try { portFromLine("x"); } catch (IOException e) { }
    }
}
