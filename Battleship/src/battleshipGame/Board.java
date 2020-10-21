package battleshipGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Board extends Parent {
	public static final int SIZE_OF_BOARD = 10;
	public static final int MAX_NUMBER_OF_SHIPS = 10;
	public static final int MAX_LENGTH_OF_SHIP = 4;
	public static final int MAX_ATTEMPS_NUMBER = 1000;
	public static final int TIMES_TO_PLACE_BIGGEST_SHIP = 1;
	public static final double CHANCE_TO_BE_VERTICAL = 0.5;
	private int shipsToPlace = MAX_NUMBER_OF_SHIPS;
    private int shipsSurvived = MAX_NUMBER_OF_SHIPS;
    private int currentLengthOfShip = MAX_LENGTH_OF_SHIP;
    private VBox rows = new VBox();
    public boolean botFinishedHisMove = true;
    
    public static boolean itIsTimeToKill(double key) {
    	return Math.random() < key ? true : false;
    }
    
    public int getShipsSurvivedNumber() {
    	return this.shipsSurvived;
    }
    
    public int getShipsToPlaceNumber() {
    	return this.shipsToPlace;
    }
    
    public Board(EventHandler<MouseEvent> handler) {
        for (int y = 0; y < SIZE_OF_BOARD; y++) {
            HBox row = new HBox();
            for (int x = 0; x < SIZE_OF_BOARD; x++) {
            	Cell cell = new Cell(x, y);
                cell.setOnMouseClicked(handler);
                row.getChildren().add(cell);
            }
            this.rows.getChildren().add(row);
        }
        getChildren().add(this.rows);
    }
    
    public void hideShipsFromEnemy() {
    	for (int y = 0; y < SIZE_OF_BOARD; y++) {
    		for (int x = 0; x < SIZE_OF_BOARD; x++) {
    			Cell cell = getCell(x, y);
    			cell.paintToLightgray();
    		}
    	}
    }
    
    public void placeShipByMouseClickOnBoard(MouseEvent event) {
    	Cell cell = (Cell) event.getSource();
        if (placeShip(new Ship(this.currentLengthOfShip, event.getButton() == MouseButton.PRIMARY), cell.getXCoord(), cell.getYCoord())) {		            	
        	if (shipsToPlace == MAX_NUMBER_OF_SHIPS - 1 || shipsToPlace == MAX_NUMBER_OF_SHIPS - 3 || 
        			shipsToPlace == MAX_NUMBER_OF_SHIPS - 6 || shipsToPlace == 0) {
        		this.currentLengthOfShip--;
        	}
        }	
    }
    
    private boolean isShipVertical() {
    	return Math.random() < CHANCE_TO_BE_VERTICAL ? true : false;
    }
    
    public void placeShipsRandomly() {
    	clearBoard();
    	Random random = new Random();
    	int shipLength = MAX_LENGTH_OF_SHIP;
    	int timesToPlaceShipOfSuchLength = TIMES_TO_PLACE_BIGGEST_SHIP;
    	while (shipLength > 0) {
    		int x = random.nextInt(SIZE_OF_BOARD);
            int y = random.nextInt(SIZE_OF_BOARD);            
            if (placeShip(new Ship(shipLength, isShipVertical()), x, y)) {
            	timesToPlaceShipOfSuchLength--;
            	if (timesToPlaceShipOfSuchLength == 0) {
            		shipLength--;
            		timesToPlaceShipOfSuchLength = MAX_LENGTH_OF_SHIP - shipLength + 1;
            	}
            }
    	}
    }
    
    public void clearBoard() {
    	for (int y = 0; y < SIZE_OF_BOARD; y++) {
    		for (int x = 0; x < SIZE_OF_BOARD; x++) {
    			Cell cell = getCell(x, y);
    			cell.setShip(null);
    			cell.paintToLightgray();
    		}
    	}
    	shipsToPlace = MAX_NUMBER_OF_SHIPS;
    	currentLengthOfShip = MAX_LENGTH_OF_SHIP;
    }
    
    public boolean placeShip(Ship ship, int x, int y) {
        if (canPlaceShip(ship, x, y)) {
            int length = ship.getLength();
            if (ship.isVertical()) {
                for (int i = y; i < y + length; i++) {
                    Cell cell = getCell(x, i);
                    cell.setShip(ship);
                    cell.paintToWhite();
                }
            }
            else {
                for (int i = x; i < x + length; i++) {
                    Cell cell = getCell(i, y);
                    cell.setShip(ship);
                    cell.paintToWhite();
                }
            }
            this.shipsToPlace--;
            return true;
        }
        return false;
    }

    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    private Cell[] getNeighbors(int x, int y) {
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y + 1), new Point2D(x, y + 1), new Point2D(x + 1, y + 1),
                new Point2D(x - 1, y), new Point2D(x + 1, y),
                new Point2D(x - 1, y - 1), new Point2D(x, y - 1), new Point2D(x + 1, y - 1)        
        };
        List<Cell> neighbors = new ArrayList<Cell>();
        for (Point2D point : points) {
            if (isValidPoint(point)) {
                neighbors.add(getCell((int)point.getX(), (int)point.getY()));
            }
        }
        return neighbors.toArray(new Cell[0]);
    }

    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.getLength();
        if (ship.isVertical()) {
            for (int i = y; i < y + length; i++) {
            	if (!pointIsAppropriateToPlaceShip(x, i)) {
            		return false;
            	}
            } 
        }
        else {
            for (int i = x; i < x + length; i++) {
            	if (!pointIsAppropriateToPlaceShip(i, y)) {
            		return false;
            	}
            }
        }
        return true;
    }
    
    private boolean pointIsAppropriateToPlaceShip(int x, int y) {
    	if (!isValidPoint(x, y)) {
        	return false;
        }                   
        Cell cell = getCell(x, y);
        if (cell.getShip() != null) {
            return false;
        }
        for (Cell neighbor : getNeighbors(x, y)) {                
            if (neighbor.getShip() != null) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < SIZE_OF_BOARD && y >= 0 && y < SIZE_OF_BOARD;
    }
    
    public DataForShot shoot(Cell cell) {
    	DataForShot data = new DataForShot();
    	data.setShootedCell(cell);
		cell.setWasShotState(true);
		Ship ship = cell.getShip();
		if (ship != null) {
			data.setIsHitShip(true);
			ship.hit();
			if (!ship.isAlive()) {
				data.setCellsAroundShip(destroyShip(ship));
			}
		}
		else {
			data.setIsHitShip(false);
			data.setCellsAroundShip(null);
		}
		return data; 
    }
    
    public void shootForHuman(Cell cell) {
    	cell.setWasShotState(true);
    	cell.paintToOrchid(); 
    	Ship ship = cell.getShip();
    	if (ship != null) {
    		ship.hit();
    		cell.paintToRed();
    		if (!ship.isAlive()) {
    			destroyShip(ship);
    		}
    	}
    }
    
    public ArrayList<DataForShot> almostKillSpecifiedShip(Ship ship) {
    	ArrayList<DataForShot> dataForShots = new ArrayList<DataForShot>();
    	if (ship == null) {
    		return null;
    	}    	
    	if (ship.getLength() == 1) {
    		dataForShots = killSpecifiedShip(ship);
    		return dataForShots;
    	}   
		for (Cell cell : getHealthyCellsOfShip(ship)) { 
			dataForShots.add(shoot(cell));
			if (ship.getHealth() == 1) {
				break;
			}
		}
		return dataForShots;
    	
    }
    
    public DataForShot makeShotToVoidIfPossible() {
    	DataForShot data = new DataForShot();
    	int attempts = 0;
    	while (true) {
    		attempts++;
	    	Random random = new Random();
	    	int x = random.nextInt(SIZE_OF_BOARD);
	    	int y = random.nextInt(SIZE_OF_BOARD);
	    	Cell cell = getCell(x, y);
	    	if (cell.getShip() == null && !cell.getWasShotState()) {
	    		data = shoot(cell);
	    		return data;
	    	}
	    	if (attempts > MAX_ATTEMPS_NUMBER) {
	    		cell = chooseCellForShot();
	    		if (cell == null) {
	    			return null;
	    		}
	    		data = shoot(cell);
	    		return data;
	    	}
    	}
    }
    
    public Cell chooseCellForShot() {
    	for (int y = 0; y < SIZE_OF_BOARD; y++) {
    		for (int x = 0; x < SIZE_OF_BOARD; x++) {
    			Cell cell = getCell(x, y);
    			if (!cell.getWasShotState()) {
    				return cell;
    			}
    		}
    	}
    	return null;
    }
   
    public boolean cellIsEmpty(int x, int y) {
    	Point2D point = new Point2D(x, y);
    	if (isValidPoint(point)) {
    		return !getCell(x, y).getWasShotState() ? true : false;
    	}
    	return false;
    }
        
    public ArrayList<DataForShot> killSpecifiedShip(Ship ship) {
    	ArrayList<DataForShot> dataForShots = new ArrayList<DataForShot>();
    	for (Cell cell : getHealthyCellsOfShip(ship)) {
    		dataForShots.add(shoot(cell));
    	}
    	return dataForShots;
    }
    
    public ArrayList<DataForShot> killDamagedShip() {
    	Ship ship = detectDamagedShip();
    	ArrayList<DataForShot> dataForShots = new ArrayList<DataForShot>();
    	if (ship != null) {
    		dataForShots = killSpecifiedShip(ship);
    	}
    	else {
    		return null;
    	}
    	return dataForShots;
    }
    
    public Ship detectDamagedShip() {    
    	for (int y = 0; y < SIZE_OF_BOARD; y++) {
    		for (int x = 0; x < SIZE_OF_BOARD; x++) {
    			Ship ship = getCell(x, y).getShip();
    			if (ship != null && ship.getHealth() < ship.getLength() && ship.getHealth() > 0) {
    				return ship;
    			}
    		}
    	}
    	return null;
    }
    
    public Ship detectRandomShip() {  
    	Random random = new Random();
    	int attempts = 0;
    	while (true) {
    		attempts++;
	    	int x = random.nextInt(SIZE_OF_BOARD);
	        int y = random.nextInt(SIZE_OF_BOARD);
	        Ship ship = getCell(x, y).getShip();
	        if (ship != null && ship.isAlive()) { 
	        	return ship;
	        }	        
	        if (attempts > MAX_ATTEMPS_NUMBER) {
	        	if (!boardStillHasAliveShip()) {
	        		return null;
	        	}
	        	attempts = 0;
	        }
    	}
    }
      
    public boolean boardStillHasAliveShip() {
    	for (int y = 0; y < SIZE_OF_BOARD; y++) {
    		for (int x = 0; x < SIZE_OF_BOARD; x++) {
    			Cell cell = getCell(x, y);
    			Ship ship = cell.getShip();
    			if (ship != null && ship.isAlive()) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    
    public ArrayList<Cell> getHealthyCellsOfShip(Ship ship) {
    	ArrayList<Cell> cellsOfShip = new ArrayList<Cell>();
    	for (int y = 0; y < SIZE_OF_BOARD; y++) {
    		for (int x = 0; x < SIZE_OF_BOARD; x++) {
    			Cell cell = getCell(x, y);
    			if (!cell.getWasShotState() && cell.getShip() == ship) {
    				cellsOfShip.add(cell);
    			}
    		}
    	}
    	return cellsOfShip;
    }
    
    public ArrayList<Cell> getCellsAroundShip(Ship ship) {
    	ArrayList<Cell> cellsAroundShip = new ArrayList<Cell>();
    	for (Cell cellOfShip : getCellsOfShip(ship)) {
    		for (Cell cell : getNeighbors(cellOfShip.getXCoord(), cellOfShip.getYCoord())) {
    			if (!cellsAroundShip.contains(cell)) {
    				cellsAroundShip.add(cell);
    			}
    		}
    	}
    	for (Cell cellOfShip : getCellsOfShip(ship)) {
    		cellsAroundShip.remove(cellOfShip);
    	}
    	return cellsAroundShip;
    }
    
    public ArrayList<Cell> destroyShip(Ship ship) {
    	ArrayList<Cell> openedCells = new ArrayList<Cell>();
    	this.shipsSurvived--;
    	for (Cell cell : getCellsAroundShip(ship)) {
    		cell.setWasShotState(true);
    		openedCells.add(cell);
    	}
    	return openedCells;
    }
    
    public ArrayList<Cell> getCellsOfShip(Ship ship) {
    	ArrayList<Cell> cellsOfShip = new ArrayList<Cell>();
    	for (int y = 0; y < SIZE_OF_BOARD; y++) {
    		for (int x = 0; x < SIZE_OF_BOARD; x++) {
    			Cell cell = getCell(x, y);
    			if (cell.getShip() == ship) {
    				cellsOfShip.add(cell);
    			}
    		}
    	}
    	return cellsOfShip;
    }
}
