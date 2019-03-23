package com.trader.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    @Test
    void add() {
        int expected = 2;
        int actual = MathUtils.add(1, 1);
        assertEquals(expected, actual);
    }
}