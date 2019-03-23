package com.trader.utils;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testing MathUtils")
class MathUtilsTest {

    MathUtils mathUtils;

    @BeforeAll
    void init(){
        mathUtils = new MathUtils();
    }

    @Nested
    @DisplayName("add method")
    class AddTest {

        @Test
        @DisplayName("when adding two positive numbers")
        void addPositive() {
            assertEquals(2, mathUtils.add(1, 1), "should return right sum");
        }


        @Test
        @DisplayName("when adding positive and negative numbers")
        void addPositiveAndNegative() {
            assertEquals(0, mathUtils.add(1, -1), "should return right sum");
        }


        @Test
        @DisplayName("when adding two negative numbers")
        void addNegative() {
            assertEquals(-2, mathUtils.add(-1, -1), "should return right sum");
        }
    }


    @Test
    @DisplayName("Division of two numbers")
    //@Disabled
    void divide() {
       // boolean isServerUp = false;
       // System.out.println(System.getProperty("ENV"));
      //  Assumptions.assumeTrue(isServerUp);
        assertThrows(ArithmeticException.class,() -> mathUtils.divide(1, 0),
                "Arithmetic Exception found");
    }

    @Test
    @DisplayName("Multiplication of two numbers")
    void multiply() {
       assertAll(()-> assertEquals(4, mathUtils.multiply(2, 2)),
               ()-> assertEquals(0, mathUtils.multiply(2, 0)),
               ()-> assertEquals(-4, mathUtils.multiply(2, -2)),
               ()-> assertEquals(4, mathUtils.multiply(-2, -2)));
    }
}