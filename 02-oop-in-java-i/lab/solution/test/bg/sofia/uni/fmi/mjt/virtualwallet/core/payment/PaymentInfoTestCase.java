package bg.sofia.uni.fmi.mjt.virtualwallet.core.payment;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PaymentInfoTestCase {
    private static final double DELTA = 1e-15;

    private static final String REASON = "Happy Bar & Grill";
    private static final String LOCATION = "Sofia";
    private static final double COST = 50.0;

    private static PaymentInfo paymentInfo;

    @BeforeClass
    public static void setUpBeforeClass() {
        paymentInfo = new PaymentInfo(REASON, LOCATION, COST);
    }

    @Test
    public void testPaymentInfoGetters() {
        assertEquals(REASON, paymentInfo.getReason());
        assertEquals(LOCATION, paymentInfo.getLocation());
        assertEquals(COST, paymentInfo.getCost(), DELTA);
    }

}
