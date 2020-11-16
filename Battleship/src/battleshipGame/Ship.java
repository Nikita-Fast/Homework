package battleshipGame;

import javafx.scene.Parent;

public class Ship extends Parent {
    private int length;
    private boolean vertical = true;
    private int health;

    public Ship(int length, boolean vertical) {
        this.length = length;
        this.vertical = vertical;
        this.health = length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
    	return this.length;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public boolean isVertical() {
    	return this.vertical;
    }

    public void hit() {
        this.health--;
    }

    public boolean isAlive() {
        return this.health > 0;
    }
    
    public int getHealth() {
    	return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
