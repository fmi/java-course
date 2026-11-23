/*
 * ============================================================================
 *  LAB 06 · INTERACTIVE SNIPPET — Spy vs Mock, side by side
 * ============================================================================
 *
 *  Same scenario, tested two ways — so you see when a spy wins over a mock.
 *
 *  The object under test: an AuditingNotifier (continues the Lab-02 Notifier
 *  idea). Its notify() does two things:
 *     1. records the message to an in-memory audit log   ← real behavior we want
 *     2. "sends" it over the network (slow, external)    ← behavior we must not run in a test
 *
 *  We want to check that the audit log captured the right message — without
 *  actually hitting the network. That is exactly the spy's sweet spot: keep the
 *  real object, stub just some method(s).
 *
 *    1. PREDICT each assertion's outcome before running.
 *    2. Run with JUnit. Both tests pass — but notice what each COULD and COULDN'T do.
 *    3. Read the two approaches side by side. Which proves more about the real code?
 *
 * ============================================================================
 */
package snippets.interactive;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SpyVsMockDemo {

    // A real class with behavior worth keeping (the audit log) plus one side effect we can't run in a test.
    static class AuditingNotifier {
        private final List<String> auditLog = new ArrayList<>();

        void notify(String recipient, String message) {
            auditLog.add(recipient + ": " + message);
            sendOverNetwork(message);
        }

        void sendOverNetwork(String message) {
            // Imagine a real HTTP/SMS call here — slow, needs the network, costs money.
            throw new IllegalStateException("real network call — must never run in a unit test!");
        }

        List<String> auditLog() {
            return auditLog;
        }
    }

    // ========================================================================
    // THE SPY WAY — real object, stub one method, keep the rest real
    // ========================================================================
    @Test
    void spy_keepsRealAuditLog_whileSuppressingNetwork() {
        AuditingNotifier spy = spy(new AuditingNotifier());

        doNothing().when(spy).sendOverNetwork(org.mockito.ArgumentMatchers.anyString());

        spy.notify("Мария", "пожар");

        assertEquals(1, spy.auditLog().size());                       // PREDICT: ______
        assertEquals("Мария: пожар", spy.auditLog().get(0));          // PREDICT: ______
                                                                      // PREDICT: test passes? ______
        verify(spy).sendOverNetwork("пожар");                         // PREDICT: verify passes? ______
    }

    // ========================================================================
    // THE MOCK WAY — fully fake object, no real behavior at all
    // ========================================================================
    @Test
    void mock_cannotObserveRealAuditLog() {
        AuditingNotifier mock = mock(AuditingNotifier.class);

        mock.notify("Мария", "пожар");

        assertTrue(mock.auditLog().isEmpty());                        // PREDICT: ______
        verify(mock).notify("Мария", "пожар");                        // PREDICT: passes? ______
    }

    /*
     * ------------------------------------------------------------------------
     *  SIDE-BY-SIDE — the point of this snippet
     * ------------------------------------------------------------------------
     *    SPY : real AuditingNotifier runs → we test its ACTUAL logic (the audit log),
     *          and suppress only the one method we can't run (sendOverNetwork).
     *    MOCK: hollow fake → real logic never runs → we can only verify that a method
     *          was called, NOT that it did the right thing internally.
     *
     *  Use a SPY when you have a real object whose behavior you want to exercise,
     *  but one method must be stubbed out.
     *
     *  🧠 WHEN NOT TO SPY: a spy is really a tool for EXISTING / legacy code you can't
     *     (or don't want to) redesign. When you control the design, prefer splitting the
     *     side effect into a SEPARATE injected dependency and MOCK that — it's cleaner and
     *     the dependency is explicit. Reach for spy sparingly; mock an injected dependency first.
     * ------------------------------------------------------------------------
     */
}
