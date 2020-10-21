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
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class EasyBot extends BasicPlayer{
	
	public ArrayList<DataForShot> makeMove(Board board) {
		ArrayList<DataForShot> dataForShots = new ArrayList<DataForShot>();
		Random random = new Random();
		boolean isHit = true;
		while (isHit) {
            int x = random.nextInt(Board.SIZE_OF_BOARD);
            int y = random.nextInt(Board.SIZE_OF_BOARD);
            Cell cell = board.getCell(x, y);
            if (cell.getWasShotState()) {
                continue;
            }
            dataForShots.add(board.shoot(cell));
            isHit = cell.shotWasSuccessfull();
            if (board.getShipsSurvivedNumber() == 0) {
                return dataForShots; 
            }
        }
		return dataForShots;
	}
	
	public void animateMove(ArrayList<DataForShot> dataForShots, Board board) {
		board.botFinishedHisMove = false;
		int j = 0;
		ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
		for (DataForShot data : dataForShots) { //create a keyFrame for every shot
			ArrayList<KeyValue> keyValues = new ArrayList<KeyValue>();
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
		    if (j == dataForShots.size() - 1) {
		    	KeyFrame keyFrame = new KeyFrame(Duration.millis(DURATION), event -> {
		    		board.botFinishedHisMove = true;
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
