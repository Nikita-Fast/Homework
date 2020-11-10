package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
//import org.junit.jupiter.api.Test;

import application.Board;

public class BoardTest {
	
	@Test
	public void isMiltipleOf() {
		assertEquals(true, Board.isMultipleOf(6, 2));
		assertEquals(false, Board.isMultipleOf(7, 3));
		assertEquals(false, Board.isMultipleOf(71, 98));
		assertEquals(true, Board.isMultipleOf(-18, 6));
	}
	
	@Test
	public void createString() {
		assertEquals("-7/13", Board.createString(-7, 13));
		assertEquals("12", Board.createString(3, 0.25));
		assertEquals("-24", Board.createString(-3, 0.125));
	}
}
