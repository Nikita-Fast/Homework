package Players;

import java.util.Random;

import battleshipGame.Board;
import battleshipGame.Cell;
import battleshipGame.Decorations;
import javafx.scene.layout.BorderPane;

public class EasyBot extends BasicPlayer{
	
	public boolean shoot(Board board) {
		Random random = new Random();
		boolean enemyTurn = true;
		while (enemyTurn) {
            int x = random.nextInt(Board.SIZE_OF_BOARD);
            int y = random.nextInt(Board.SIZE_OF_BOARD);
            Cell cell = board.getCell(x, y);
            if (cell.getWasShotState()) {
                continue;
            }
            
            
            enemyTurn = board.shoot(cell);
            if (board.getShipsSurvivedNumber() == 0) {
                return true; //return true-value if all ships dead
            }
        }
		return false; //return false-value if some ships survived
	}
	
}
