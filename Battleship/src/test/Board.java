package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board extends Parent {
	public static final int SIZE_OF_BOARD = 10;
	public static final int MAX_NUMBER_OF_SHIPS = 10;
	public static final int MAX_LENGTH_OF_SHIP = 4;
	public static final int MAX_ATTEMPS_NUMBER = 1000;
    private VBox rows = new VBox();
    private boolean enemy = false;
    public int shipsToPlace = MAX_NUMBER_OF_SHIPS;
    public int shipsSurvived = MAX_NUMBER_OF_SHIPS;

    public Board(boolean enemy, EventHandler<MouseEvent> handler) {
        this.enemy = enemy;
        for (int y = 0; y < SIZE_OF_BOARD; y++) {
            HBox row = new HBox();
            for (int x = 0; x < SIZE_OF_BOARD; x++) {
                Cell cell = new Cell(x, y, this);
                cell.setOnMouseClicked(handler);
                row.getChildren().add(cell);
            }
            this.rows.getChildren().add(row);
        }
        getChildren().add(this.rows);
    }
    
    public void hideShipsFromEnemy() {
    	for (int y = 0; y < 10; y++) {
    		for (int x = 0; x < 10; x++) {
    			Cell cell = getCell(x, y);
    			cell.paintToLightgray();
    		}
    	}
    }
    
    public void placeShipsRandomly() {
    	clearBoard();
    	Random random = new Random();
    	int shipLength = MAX_LENGTH_OF_SHIP;
    	int timesToPlaceShipOfSuchLength = 1;
    	while (shipLength > 0) {
    		int x = random.nextInt(SIZE_OF_BOARD);
            int y = random.nextInt(SIZE_OF_BOARD);            
            if (placeShip(new Ship(shipLength, Math.random() < 0.5), x, y)) {
            	timesToPlaceShipOfSuchLength--;
            	if (timesToPlaceShipOfSuchLength == 0) {
            		shipLength--;
            		timesToPlaceShipOfSuchLength = MAX_LENGTH_OF_SHIP - shipLength + 1;
            	}
            	/*
            	if (shipsToPlace == 9 || shipsToPlace == 7 || shipsToPlace == 4 || shipsToPlace == 0) {
            		shipLength--;
            	}*/
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
            	/*
                if (!isValidPoint(x, i)) {
                	return false;
                }                   
                Cell cell = getCell(x, i);
                if (cell.getShip() != null) {
                    return false;
                }
                for (Cell neighbor : getNeighbors(x, i)) {                
                    if (neighbor.getShip() != null) {
                        return false;
                    }
                }*/
            } 
        }
        else {
            for (int i = x; i < x + length; i++) {
            	if (!pointIsAppropriateToPlaceShip(i, y)) {
            		return false;
            	}
            	/*
                if (!isValidPoint(i, y)) {
                	return false;
                }
                Cell cell = getCell(i, y);
                if (cell.getShip() != null) {
                	return false;
                }
                for (Cell neighbor : getNeighbors(i, y)) {                   
                    if (neighbor.getShip() != null) {
                    	return false;
                    }                        
                }*/
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
/*
    public class Cell extends Rectangle {
        public int x, y;
        public Ship ship = null;
        public boolean wasShot = false;
        private Board board;

        public Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTGRAY);
            setStroke(Color.CYAN);
        }
        
        public int getXCoord() {
        	return x;
        }
        
        public int getYCoord() {
        	return y;
        }

        public boolean shoot() {
            wasShot = true;
            setFill(Color.ORCHID);

            if (ship != null) {
                ship.hit();
                setFill(Color.RED);
                if (!ship.isAlive()) {
                	destroyShip(ship);
                    board.shipsSurvived--;
                }
                return true;
            }

            return false;
        }
        
        public boolean hasThisShip(Ship ship) {
        	return this.ship == ship ? true : false;
        }
        
    }
*/  
    public boolean shoot(Cell cell) {
    	cell.setWasShotState(true);
    	cell.paintToOrchid();
    	Ship ship = cell.getShip();
    	if (ship != null) {
    		ship.hit();
    		cell.paintToRed();
    		if (!ship.isAlive()) {
    			destroyShip(ship);
    		}
    		return true;
    	}
    	return false;
    }
    
    public void almostKillSpecifiedShip(Ship ship) {
    	if (ship == null) {
    		return;
    	}    	
    	if (ship.getLength() == 1) {
    		killSpecifiedShip(ship);
    		return;
    	}   	
    	while (true) {
    		for (Cell cell : getHealthyCellsOfShip(ship)) {
    			//cell.shoot();
    			shoot(cell);
    			if (ship.getHealth() == 1) {
    				return;
    			}
    		}
    	}
    }
    
    public void makeShotToVoidIfPossible() {
    	boolean end = false;
    	int attempts = 0;
    	while (!end) {
    		attempts++;
	    	Random random = new Random();
	    	int x = random.nextInt(SIZE_OF_BOARD);
	    	int y = random.nextInt(SIZE_OF_BOARD);
	    	Cell cell = getCell(x, y);
	    	if (cell.getShip() == null && !cell.getWasShotState()) {
	    		//cell.shoot();
	    		shoot(cell);
	    		end = true;
	    	}
	    	if (attempts > MAX_ATTEMPS_NUMBER) {
	    		cell = chooseCellForShot();
	    		if (cell == null) {
	    			return;
	    		}
	    		//chooseCellForShot().shoot(); почему здесь просто не использовать cell.shoot();?
	    		shoot(cell);  //а если передадут null?
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
        
    public void killSpecifiedShip(Ship ship) {
    	for (Cell cell : getHealthyCellsOfShip(ship)) {
    		//cell.shoot();
    		shoot(cell);
    	}
    }
    
    public void killDamagedShip() {
    	Ship ship = detectDamagedShip();
    	if (ship != null) {
    		killSpecifiedShip(ship);
    	}
    }
    
    public Ship detectDamagedShip() {    //что делать если вернули null?
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
    
    public Ship detectRandomShip() {  //что делать если вернули null?
    	Random random = new Random();
    	int attempts = 0;
    	while (true) {
    		attempts++;
	    	int x = random.nextInt(SIZE_OF_BOARD);
	        int y = random.nextInt(SIZE_OF_BOARD);
	        Ship ship = getCell(x, y).getShip();
	        if (ship != null && ship.isAlive()) { //if (getCell(x, y).getShip() != null && !getCell(x, y).getWasShotState())
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
    	return cellsAroundShip;
    }
    
    public void destroyShip(Ship ship) {
    	this.shipsSurvived--;
    	for (Cell cell : getCellsAroundShip(ship)) {
    		cell.setWasShotState(true);
    		cell.paintToOrchid();
    	}
    	for (Cell cell : getCellsOfShip(ship)) {
    		cell.paintToRed();
    	}
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
