package com.trader.utils;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MathUtilsTest {

    MathUtils mathUtils;

    @BeforeAll
    void init(){
        mathUtils = new MathUtils();
    }

    @Test
    @DisplayName("Addtion of two numbers")
    void add() {
        int expected = 2;
        int actual = mathUtils.add(1, 1);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Division of two numbers")
    //@Disabled
    void divide() {
        boolean isServerUp = false;
       // System.out.println(System.getProperty("ENV"));
        Assumptions.assumeTrue(isServerUp);
        assertThrows(ArithmeticException.class,() -> mathUtils.divide(1, 0),
                "Exception not found");
    }
}