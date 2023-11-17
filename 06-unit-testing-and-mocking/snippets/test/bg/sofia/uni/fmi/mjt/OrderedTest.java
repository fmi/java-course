package bg.sofia.uni.fmi.mjt;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderedTest {

    @BeforeAll
    static void setUpTestCase() {
        System.out.println("BeforeAll called");
    }

    @AfterAll
    static void tearDownTestCase() {
        System.out.println("AfterAll called");
    }

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach called");
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach called");
    }

    @Test
    @Order(3)
    @Disabled
    void testNullValues() {
        System.out.println("Test null values");
    } // will execute third

    @Test
    @Order(1)
    void testEmptyValues() {
        System.out.println("Test empty values");
    } // will execute first

    @Test
    @Order(2)
    void testValidValues() {
        System.out.println("Test valid values");
    } // will execute second

    @Test
    void testDepositPerformance() {
        System.out.println("Test performance");

        assertTimeout(ofMillis(200), () -> Thread.sleep(120), //wallet.deposit(1250.0),
            "Deposit amount too slow");
    }

}
