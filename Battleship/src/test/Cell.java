package test;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
	private int x;
	private int y;
    private Ship ship = null;
    private boolean wasShot = false;
    private Board board;

    public Cell(int x, int y, Board board) {
        super(30, 30);
        this.x = x;
        this.y = y;
        this.board = board;
        paintToLightgray();
    }
    
    public void paintToLightgray() {
    	this.setFill(Color.LIGHTGRAY);
        this.setStroke(Color.CYAN);
    }
    
    public void paintToWhite() {
    	this.setFill(Color.WHITE);
    }
    
    public void paintToOrchid() {
    	setFill(Color.ORCHID);
    }
    
    public void paintToRed() {
    	setFill(Color.RED);
    }
    
    public void setShip(Ship ship) {
    	this.ship = ship;
    }
    
    public void setWasShotState(boolean wasShot) {
    	this.wasShot = wasShot;
    }
    
    public boolean getWasShotState() {
    	return this.wasShot;
    }
    
    public Ship getShip() {
    	return this.ship;
    }
    
    public int getXCoord() {
    	return x;
    }
    
    public int getYCoord() {
    	return y;
    }
    /*
    public boolean shoot() {
        wasShot = true;
        setFill(Color.ORCHID);

        if (ship != null) {
            ship.hit();
            setFill(Color.RED);
            if (!ship.isAlive()) {
            	//destroyShip(ship);
                board.shipsSurvived--;
            }
            return true;
        }

        return false;
    }
    */
    public boolean hasThisShip(Ship ship) {
    	return this.ship == ship ? true : false;
    }
}
