package bg.sofia.uni.fmi.mjt.math;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// toggle commented line to change the explicit test method execution order
// @TestMethodOrder(MethodOrderer.MethodName.class)
public class NumberUtilsTest {

    @Test
    @Order(1)
    void testIsPrimeNegativeArgument() {
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.isPrime(-16),
            "Negative numbers are not a legal argument");
    }

    @Test
    @Order(2)
    void testIsPrimeZero() {
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.isPrime(0), "Zero is not a legal argument");
    }

    @Test
    @Order(3)
    void testIsPrimeOne() {
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.isPrime(1), "One is not a legal argument");
    }

    @Test
    @Order(4)
    void testIsPrimeTwo() {
        assertTrue(NumberUtils.isPrime(2), "Two is prime");
    }

    @Test
    @Order(5)
    void testIsPrimeFour() {
        assertFalse(NumberUtils.isPrime(4), "Four is not prime");
    }

    @Test
    @Order(6)
    void testIsPrimeNine() {
        assertFalse(NumberUtils.isPrime(9), "Nine is not prime");
    }

    @Test
    @Order(7)
    void testIsPrimeEleven() {
        assertTrue(NumberUtils.isPrime(11), "Eleven is prime");
    }

    @Test
    @Order(8)
    void testIsPrimeTwelve() {
        assertFalse(NumberUtils.isPrime(12), "Twelve is not prime");
    }

}
