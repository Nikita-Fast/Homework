package test.testsForGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import battleshipGame.Cell;
import battleshipGame.Ship;


class CellTest {
	private Cell[] cells = new Cell[4];
	
	@BeforeEach
	public void setUp() {
		cells[0] = new Cell(0, 9);
		cells[1] = new Cell(9, 0);
		cells[2] = new Cell(0, 0);
		cells[3] = new Cell(9, 9);
	}
	
	@Test
	public void hasThisShipTest() {
		Ship[] ships = new Ship[] {new Ship(2, true), new Ship(3, false), new Ship(1, true)};
		for (Cell cell : cells) {
			for (Ship ship : ships) {
				assertFalse(cell.hasThisShip(ship));
				cell.setShip(ship);
				assertTrue(cell.hasThisShip(ship));
			}
		}
	}
}
