package com.rajat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        System.out.println("Setting Up Calculator before Test");
        calculator = new Calculator();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Tear down Calculator after Test");
        calculator = null;
    }

    @Test
    public void add() {
        assertEquals(3, calculator.add(1, 2));
        System.out.println("Addition test passed.");
    }

    @Test
    public void sub() {
        assertEquals(-1, calculator.sub(1, 2));
        System.out.println("Subtraction test passed.");
    }

    @Test
    public void mul() {
        assertEquals(2, calculator.mul(1, 2));
        System.out.println("Multiplication test passed.");
    }

    @Test
    public void div() {
        assertEquals(5, calculator.div(10, 2));
        System.out.println("Division test passed.");
    }
}