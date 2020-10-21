package Players;

import java.util.ArrayList;
import java.util.Random;

import battleshipGame.Board;
import battleshipGame.Cell;
import battleshipGame.DataForShot;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Human extends BasicPlayer {
	
	private Cell cellForShot;

	public void setCellForShot(Cell cell) {
		cellForShot = cell;
	}
	
	public ArrayList<DataForShot> makeMove(Board board) {
		ArrayList<DataForShot> dataForShots = new ArrayList<DataForShot>();
		dataForShots.add(board.shoot(cellForShot));
		return dataForShots;
	}
	
	public void animateMove(ArrayList<DataForShot> dataForShots, Board board) {
		ArrayList<KeyValue> keyValues = new ArrayList<KeyValue>();
		DataForShot data = dataForShots.get(0); 
		Color color;
		if (data.getIsHitShip()) {
			color = Color.RED;
		}
		else {
			color = Color.ORCHID;
		}
		keyValues.add(new KeyValue(data.getShootedCell().fillProperty(), color));
		
		if (data.getCellsAroundShip() != null) {  
			for (Cell cell : data.getCellsAroundShip()) {
				keyValues.add(new KeyValue(cell.fillProperty(), Color.ORCHID));
			}
		}
		KeyValue[] values = new KeyValue[keyValues.size()];
	    values = keyValues.toArray(values);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(DURATION), values);
		Timeline tl = new Timeline(keyFrame);
    	tl.play();
	}
	
	public void makeMoveWithAnimation(Board board) {
		animateMove(makeMove(board), board);
	}
}
