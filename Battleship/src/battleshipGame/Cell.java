package battleshipGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
	private int x;
	private int y;
    private Ship ship = null;
    private boolean wasShot = false;

    public Cell(int x, int y) {
        super(30, 30);
        this.x = x;
        this.y = y;
        paintToLightgray();
    }
    
    public void printCoords() {
    	System.out.println(x + " " + y);
    }
    
    public boolean shotWasSuccessfull() {
    	return this.ship != null ? true : false;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXCoord() {
    	return x;
    }
    
    public int getYCoord() {
    	return y;
    }
   
    public boolean hasThisShip(Ship ship) {
    	return this.ship == ship ? true : false;
    }
}
