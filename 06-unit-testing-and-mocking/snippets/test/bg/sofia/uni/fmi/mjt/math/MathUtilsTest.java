import bg.sofia.uni.fmi.mjt.testing.math.MathUtils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MathUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIsPrimeIllegalArgument() {
        MathUtils.isPrime(-16);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsPrimeZero() {
        MathUtils.isPrime(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsPrimeOne() {
        MathUtils.isPrime(1);
    }

    @Test
    public void testIsPrimeTwo() {
        assertTrue("", MathUtils.isPrime(2));
    }

    @Test
    public void testIsPrimeFour() {
        assertFalse("", MathUtils.isPrime(4));
    }

    @Test
    public void testIsPrimeNine() {
        assertFalse("", MathUtils.isPrime(9));
    }

    @Test
    public void testIsPrimeEleven() {
        assertTrue("", MathUtils.isPrime(11));
    }

    @Test
    public void testIsPrimeTwelve() {
        assertFalse("", MathUtils.isPrime(12));
    }

}
