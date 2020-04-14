package com.company.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FerrariTest {

    private Ferrari myferrari;

    @BeforeEach
    public void setUp() {
        myferrari = new Ferrari(800, 2, "812 Superfast", 2.9f);
    }

    @Test
    void getInfo() {
        String actual = myferrari.getInfo();
        String expected = "Ferrari 812 Superfast is 2 seats car" + "\nengine power is 800\n" +
                "acceleration from 0 to 100 km/h takes 2.9 seconds";
        assertEquals(expected, actual);
    }
}