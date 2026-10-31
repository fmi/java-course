/**
 * ============================================================================
 *  LAB 03 · INTERACTIVE SNIPPET
 * ============================================================================
 *
 *  A config value fails to parse deep in a "parser". Three versions of the
 *  loader sit above it. Both print an error — but only one lets you DEBUG it.
 *
 *    1. Fill in all PREDICTs
 *    2. Run and compare the two printed stack traces.
 *    3. (Optional, debugger): breakpoint in parsePort(), inspect the exception
 *       object — follow its getCause() chain in the lossy vs. the chained case.
 * ============================================================================
 */
public class ExceptionCauseChain {

    // ---------------- persistence-ish layer ----------------
    static int parsePort(String raw) {
        return Integer.parseInt(raw);
    }

    // ---------------- a business exception the upper layer understands -------
    static final class ConfigException extends RuntimeException {
        ConfigException(String message, Throwable cause) {
            super(message, cause);
        }
        ConfigException(String message) {
            super(message);
        }
    }

    // VERSION 1
    static int loadPortLossy(String raw) {
        try {
            return parsePort(raw);
        } catch (NumberFormatException e) {
            throw new ConfigException("bad port in config");
        }
    }

    // VERSION 2
    static int loadPortChained(String raw) {
        try {
            return parsePort(raw);
        } catch (NumberFormatException e) {
            throw new ConfigException("bad port in config", e);
        }
    }

    // VERSION 3
    static int loadPortWithCleanup(String raw) {
        try {
            System.out.println("  opening stream");
            return parsePort(raw);
        } catch (NumberFormatException e) {
            throw new ConfigException("bad port in config", e);
        } finally {
            System.out.println("  closing stream");
            return 7777;
        }
    }

    public static void main(String... args) {
        String badConfig = "localhost";

        System.out.println("===== LOSSY =====");
        try {
            loadPortLossy(badConfig);
        } catch (ConfigException ex) {
            ex.printStackTrace(System.out);
            System.out.println("getCause() = " + ex.getCause());   // PREDICT: ______
        }

        System.out.println("\n===== CHAINED =====");
        try {
            loadPortChained(badConfig);
        } catch (ConfigException ex) {
            ex.printStackTrace(System.out);
            System.out.println("getCause() = " + ex.getCause());   // PREDICT: ______
        }

        System.out.println("\n===== FINALLY (happy path) =====");
        int port = loadPortWithCleanup("8080");
        System.out.println("loaded port = " + port);               // PREDICT: ______

        System.out.println("\n===== FINALLY (throwing path) =====");
        try {
            loadPortWithCleanup(badConfig);                        // PREDICT: does "closing stream" still print? ______
        } catch (ConfigException ex) {
            System.out.println("caught: " + ex.getMessage());      // PREDICT: ______
        }
    }
}
