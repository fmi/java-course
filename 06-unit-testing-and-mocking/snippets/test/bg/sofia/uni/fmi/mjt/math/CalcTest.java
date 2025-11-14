package bg.sofia.uni.fmi.mjt.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalcTest {

    Calc calc = new Calc();

    @Test
    void testMultiplyXByZeroShouldReturnZero() {
        assertEquals(0, calc.multiply(10, 0), "10 x 0 must be 0");
    }

    @Test
    void testMultiplyZeroByXShouldReturnZero() {
        assertEquals(0, calc.multiply(0, 10), "0 x 10 must be 0");
    }

    @Test
    void testMultiplyZeroByZeroShouldReturnZero() {
        assertEquals(0, calc.multiply(0, 0), "0 x 0 must be 0");
    }

}
