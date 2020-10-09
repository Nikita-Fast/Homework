package Players;

import battleshipGame.Board;
import battleshipGame.Cell;

public class Human extends BasicPlayer {
	
	private Cell cellForShot;

	@Override
	public boolean shoot(Board board) {
		return board.shoot(cellForShot);
	}

	public void setCellForShot(Cell cell) {
		cellForShot = cell;
	}
}
