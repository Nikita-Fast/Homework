package test.testsForGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import battleshipGame.Ship;

class ShipTest {

private Ship ship1, ship4;
	
	@BeforeEach
	public void setUp() {
		boolean isVertical = true;
		ship1 = new Ship(1, isVertical);
		ship4 = new Ship(4, isVertical);
	}
	
	@Test
	public void hitTest() {
		assertEquals(ship4.getLength(), ship4.getHealth());
		ship4.hit();
		assertEquals(ship4.getLength() - 1, ship4.getHealth());
		
		assertEquals(ship1.getLength(), ship1.getHealth());
		ship1.hit();
		assertEquals(ship1.getLength() - 1, ship1.getHealth());
	}
	
	@Test
	public void isAliveTest() {
		assertTrue(ship4.isAlive());
		while (ship4.getHealth() > 1) {
			ship4.hit();
			assertTrue(ship4.isAlive());
		}
		ship4.hit();
		assertFalse(ship4.isAlive());
		
		assertTrue(ship1.isAlive());
		ship1.hit();
		assertFalse(ship1.isAlive());
	}
}
