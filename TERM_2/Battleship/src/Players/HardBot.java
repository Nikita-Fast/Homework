package Players;

import java.util.ArrayList;
import java.util.Random;

import battleshipGame.Board;
import battleshipGame.Cell;
import battleshipGame.DataForShot;
import battleshipGame.Decorations;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class HardBot extends BasicPlayer{
	
	private static final double DIFFICULTY = 0.29; //set value from 0 to 1; the closer to 1, the harder game is
	
	public ArrayList<DataForShot> makeMove(Board board) {
		ArrayList<DataForShot> dataForShots = new ArrayList<DataForShot>();
		ArrayList<DataForShot> transitionalDataList = new ArrayList<DataForShot>();
		
		transitionalDataList = board.killDamagedShip();
		if (transitionalDataList != null) {
			dataForShots.addAll(transitionalDataList);
		}
		transitionalDataList = null; 
		
		if (Board.itIsTimeToKill(DIFFICULTY)) { 
			transitionalDataList = board.almostKillSpecifiedShip(board.detectRandomShip());
			if (transitionalDataList != null) {
				dataForShots.addAll(transitionalDataList);
			}
		}
		
		DataForShot transitionalData = board.makeShotToVoidIfPossible();
		if (transitionalData != null) {
			dataForShots.add(transitionalData);
		}
        if (board.getShipsSurvivedNumber() == 0) {
            return dataForShots;
        }
        return dataForShots;
	}
	
	public void animateMove(ArrayList<DataForShot> dataForShots, Board board) {
		board.setBotFinishedMove(false);
		int j = 0;
		ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
		for (DataForShot data : dataForShots) { //create a keyFrame for every shot
			ArrayList<KeyValue> keyValues = new ArrayList<KeyValue>();
			Color color;
			if (data.getIsHitShip()) {
				color = Color.ORANGE;
			}
			else {
				color = Color.ORCHID;
			}
			keyValues.add(new KeyValue(data.getShootedCell().fillProperty(), color));
			
			if (data.getCellsAroundShip() != null) {  
				for (Cell cell : data.getCellsAroundShip()) {
					keyValues.add(new KeyValue(cell.fillProperty(), Color.ORCHID));
				}
				for (Cell cell : board.getCellsOfShip(data.getShootedCell().getShip())) {
					keyValues.add(new KeyValue(cell.fillProperty(), Color.RED));
				}
			}
			KeyValue[] values = new KeyValue[keyValues.size()];
		    values = keyValues.toArray(values);
		    if (j == dataForShots.size() - 1) {
		    	KeyFrame keyFrame = new KeyFrame(Duration.millis(DURATION), event -> {
		    		board.setBotFinishedMove(true);
		    	}, 
		    	values);
		    	keyFrames.add(keyFrame);
		    }
		    else {
		    	KeyFrame keyFrame = new KeyFrame(Duration.millis(DURATION), values);
				keyFrames.add(keyFrame);
		    }
			j++;
		}
		for (int i = 0; i < keyFrames.size(); i++) {
	    	Timeline tl = new Timeline(keyFrames.get(i));
	    	tl.setDelay(Duration.seconds(DELAY * (i + 1)));
	    	tl.play();
		}
	}
	
	public void makeMoveWithAnimation(Board board) {
		animateMove(makeMove(board), board);
	}
}
