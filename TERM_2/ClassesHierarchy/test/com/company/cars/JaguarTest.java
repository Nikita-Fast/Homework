package com.company.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JaguarTest {

    private Jaguar jaguar;

    @BeforeEach
    public void setUp() {
        jaguar = new Jaguar(385, 5, "XF", "2 zone");
    }

    @Test
    void getInfo() {
        String actual = jaguar.getInfo();
        String expected = "Jaguar XF is 5 seats car" + "\nengine power is 385\n" +
                "The car is equipped with 2 zone climate control";
        assertEquals(expected, actual);
    }
}