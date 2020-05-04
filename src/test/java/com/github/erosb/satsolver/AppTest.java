package com.github.erosb.satsolver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    @Test
    void test() {
        assertTrue(new SatSolver("(not A and B) impl (A and B)").isSatisfiable());
    }

}
