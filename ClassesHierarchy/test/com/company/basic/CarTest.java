package com.company.basic;

import com.company.cars.Ferrari;
import com.company.cars.Jaguar;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {
    private Ferrari myferrari;
    private Jaguar myJaguar;

    @BeforeEach
    public void setUp() {
        myferrari = new Ferrari(800, 2, "812 Superfast", 2.9f);
        myJaguar = new Jaguar(385, 5, "XF", "2 zone");
    }

    @Test
    void getEnginePower() {
        assertEquals(800, myferrari.getEnginePower(), "ferrari did not pass test");
        assertEquals(385, myJaguar.getEnginePower(), "jaguar did not pass test");
    }

    @Test
    void getBrand() {
        assertEquals("Ferrari", myferrari.getBrand(), "ferrari did not pass test");
        assertEquals("Jaguar", myJaguar.getBrand(), "jaguar did not pass test");
    }

    @Test
    void getModelName() {
        assertEquals("812 Superfast", myferrari.getModelName(), "ferrari did not pass test");
        assertEquals("XF", myJaguar.getModelName(), "jaguar did not pass test");
    }

    @Test
    void getSeats() {
        assertEquals(2, myferrari.getSeats(), "ferrari did not pass test");
        assertEquals(5, myJaguar.getSeats(), "jaguar did not pass test");
    }
}