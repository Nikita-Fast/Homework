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
    private VBox rows = new VBox();
    private boolean enemy = false;
    public int shipsToPlace = 10;
    public int shipsSurvived = 10;

    public Board(boolean enemy, EventHandler<MouseEvent> handler) { //EventHandler<? super MouseEvent> handler
        this.enemy = enemy;
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell c = new Cell(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }

            rows.getChildren().add(row);
        }

        getChildren().add(rows);
    }
    
    public void hideShipsFromEnemy() {
    	for (int y = 0; y < 10; y++) {
    		for (int x = 0; x < 10; x++) {
    			Cell cell = getCell(x, y);
    			cell.setFill(Color.LIGHTGRAY);
                cell.setStroke(Color.CYAN);
    		}
    	}
    }
    
    public void placeShipsRandomly() {
    	clearBoard();
    	Random random = new Random();
    	int type = 4;
    	while (type > 0) {
    		int x = random.nextInt(10);
            int y = random.nextInt(10);
            
            if (placeShip(new Ship(type, Math.random() < 0.5), x, y)) {
            	if (shipsToPlace == 9 || shipsToPlace == 7 || shipsToPlace == 4 || shipsToPlace == 0) {
            		type--;
            	}
            }
    	}
    }
    
    public void clearBoard() {
    	for (int y = 0; y < 10; y++) {
    		for (int x = 0; x < 10; x++) {
    			Cell cell = getCell(x, y);
    			cell.ship = null;
    			cell.setFill(Color.LIGHTGRAY);
                cell.setStroke(Color.CYAN);
    		}
    	}
    	shipsToPlace = 10;
    }
    
    public boolean placeShip(Ship ship, int x, int y) {
        if (canPlaceShip(ship, x, y)) {
            int length = ship.type;

            if (ship.vertical) {
                for (int i = y; i < y + length; i++) {
                    Cell cell = getCell(x, i);
                    cell.ship = ship;
                    cell.setFill(Color.WHITE);
                }
            }
            else {
                for (int i = x; i < x + length; i++) {
                    Cell cell = getCell(i, y);
                    cell.ship = ship;
                    cell.setFill(Color.WHITE);
                }
            }
            shipsToPlace--;
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

        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getCell((int)p.getX(), (int)p.getY()));
            }
        }

        return neighbors.toArray(new Cell[0]);
    }

    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.type;

        if (ship.vertical) {
            for (int i = y; i < y + length; i++) {
                if (!isValidPoint(x, i)) {
                	return false;
                }                   

                Cell cell = getCell(x, i);
                if (cell.ship != null) {
                    return false;
                }

                for (Cell neighbor : getNeighbors(x, i)) {
                 
                    if (neighbor.ship != null) {
                        return false;
                    }
                }
            }
        }
        else {
            for (int i = x; i < x + length; i++) {
                if (!isValidPoint(i, y))
                    return false;

                Cell cell = getCell(i, y);
                if (cell.ship != null)
                    return false;

                for (Cell neighbor : getNeighbors(i, y)) {
                    
                    if (neighbor.ship != null)
                        return false;
                }
            }
        }

        return true;
    }

    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

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
    
    public void almostKillSpecifiedShip(Ship ship) {
    	if (ship == null) {
    		return;
    	}
    	
    	if (ship.type == 1) {
    		killSpecifiedShip(ship);
    		return;
    	}
    	
    	while (true) {
    		for (Cell cell : getHealthyCellsOfShip(ship)) {
    			cell.shoot();
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
	    	int x = random.nextInt(10);
	    	int y = random.nextInt(10);
	    	Cell cell = getCell(x, y);
	    	if (cell.ship == null && !cell.wasShot) {
	    		cell.shoot();
	    		end = true;
	    	}
	    	if (attempts > 1000) {
	    		cell = chooseCellForShot();
	    		if (cell == null) {
	    			return;
	    		}
	    		chooseCellForShot().shoot();	    		
	    	}
    	}
    }
    
    public Cell chooseCellForShot() {
    	for (int y = 0; y < 10; y++) {
    		for (int x = 0; x < 10; x++) {
    			Cell cell = getCell(x, y);
    			if (!cell.wasShot) {
    				return cell;
    			}
    		}
    	}
    	return null;
    }
   
    public boolean cellIsEmpty(int x, int y) {
    	Point2D point = new Point2D(x, y);
    	if (isValidPoint(point)) {
    		return !getCell(x, y).wasShot ? true : false;
    	}
    	return false;
    }
        
    public void killSpecifiedShip(Ship ship) {
    	for (Cell cell : getHealthyCellsOfShip(ship)) {
    		cell.shoot();
    	}
    }
    
    public void killDamagedShip() {
    	Ship ship = detectDamagedShip();
    	if (ship != null) {
    		killSpecifiedShip(ship);
    	}
    }
    
    public Ship detectDamagedShip() {
    	for (int y = 0; y < 10; y++) {
    		for (int x = 0; x < 10; x++) {
    			Cell cell = getCell(x, y);
    			if (cell.ship != null && cell.ship.getHealth() < cell.ship.type && cell.ship.getHealth() > 0) {
    				return cell.ship;
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
	    	int x = random.nextInt(10);
	        int y = random.nextInt(10);
	        
	        if (getCell(x, y).ship != null && !getCell(x, y).wasShot) {
	        	//printCoordsOfShip(getCell(x, y).ship);
	        	return getCell(x, y).ship;
	        }
	        
	        if (attempts > 10_000) {
	        	if (!boardStillHasShip()) {
	        		System.out.println("Can'tfind ship");
	        		return null;
	        	}
	        }
    	}
    }
      
    public boolean boardStillHasShip() {
    	for (int y = 0; y < 10; y++) {
    		for (int x = 0; x < 10; x++) {
    			Cell cell = getCell(x, y);
    			if (cell.ship != null) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    
    public ArrayList<Cell> getHealthyCellsOfShip(Ship ship) {
    	ArrayList<Cell> cellsOfShip = new ArrayList<Cell>();
    	for (int y = 0; y < 10; y++) {
    		for (int x = 0; x < 10; x++) {
    			Cell cell = getCell(x, y);
    			if (!cell.wasShot && cell.ship == ship) {
    				cellsOfShip.add(cell);
    			}
    		}
    	}
    	return cellsOfShip;
    }
    
    public ArrayList<Cell> getCellsAroundShip(Ship ship) {
    	ArrayList<Cell> cellsAroundShip = new ArrayList<Cell>();
    	for (Cell cellOfShip : getCellsOfShip(ship)) {
    		for (Cell cell : getNeighbors(cellOfShip.x, cellOfShip.y)) {
    			if (!cellsAroundShip.contains(cell)) {
    				cellsAroundShip.add(cell);
    			}
    		}
    	}
    	return cellsAroundShip;
    }
    
    public void destroyShip(Ship ship) {
    	for (Cell cell : getCellsAroundShip(ship)) {
    		cell.wasShot = true;
    		cell.setFill(Color.ORCHID);
    	}
    	for (Cell cell : getCellsOfShip(ship)) {
    		cell.setFill(Color.RED);
    	}
    }
    
    public ArrayList<Cell> getCellsOfShip(Ship ship) {
    	ArrayList<Cell> cellsOfShip = new ArrayList<Cell>();
    	for (int y = 0; y < 10; y++) {
    		for (int x = 0; x < 10; x++) {
    			Cell cell = getCell(x, y);
    			if (cell.ship == ship) {
    				cellsOfShip.add(cell);
    			}
    		}
    	}
    	return cellsOfShip;
    }
}
