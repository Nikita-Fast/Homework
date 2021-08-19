package Players;

import java.util.ArrayList;

import battleshipGame.Board;
import battleshipGame.Cell;
import battleshipGame.DataForShot;

public abstract class BasicPlayer {
	
	public static final double DELAY = 2.0;
	public static final double DURATION = 1.0;
	
	public abstract ArrayList<DataForShot> makeMove(Board board);
	
	public abstract void animateMove(ArrayList<DataForShot> dataForShots, Board board);
	
	public abstract void makeMoveWithAnimation(Board board);
}
