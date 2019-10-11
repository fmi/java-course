import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SampleFunnelCheckerTest {

    private static FunnelChecker funnelChecker;

    @BeforeClass
    public static void setUp() {
        funnelChecker = new FunnelChecker();
    }

    @Test
    public void testFunnelChecker_FunnelFirst() {
        assertTrue(funnelChecker.isFunnel("leave", "eave"));
    }

    @Test
    public void testFunnelChecker_FunnelAnotherLastButOne() {
        assertTrue(funnelChecker.isFunnel("dragoon", "dragon"));
    }

    @Test
    public void testFunnelChecker_FunnelLastButOne() {
        assertTrue(funnelChecker.isFunnel("reset", "rest"));
    }

    @Test
    public void testFunnelChecker_NoFunnelFirst() {
        assertFalse(funnelChecker.isFunnel("eave", "leave"));
    }

    @Test
    public void testFunnelChecker_NoFunnelScrambled() {
        assertFalse(funnelChecker.isFunnel("sleet", "lets"));
    }

    @Test
    public void testFunnelChecker_NoFunnelTwoMissing() {
        assertFalse(funnelChecker.isFunnel("skiff", "ski"));
    }

}
