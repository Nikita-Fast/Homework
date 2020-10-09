package Players;

import battleshipGame.Board;
import battleshipGame.Decorations;

public class HardBot extends BasicPlayer{
	
	private static final double DIFFICULTY = 0.99; //set value from 0 to 1; the closer to 1, the harder game is

	public boolean shoot(Board board) {
		boolean enemyTurn = true;
		while (enemyTurn) {
    		board.killDamagedShip();
    		if (Board.itIsTimeToKill(DIFFICULTY)) {  
    			board.almostKillSpecifiedShip(board.detectRandomShip());
    		}           
    		board.makeShotToVoidIfPossible();
    		enemyTurn = false;
            if (board.getShipsSurvivedNumber() == 0) {
            	/*
            	info.setText("BOT WIN");
            	Decorations.createDecorationForGameEnd(root, info);*/
                return true;
            }
        }
		return false;
	}
}
