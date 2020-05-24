package game;

import java.util.ArrayList;

public class Ship {
	
	private int length;
	private int health;
	private ArrayList<Point> points;
	
	public Ship(int length) {
		this.length = length;
		health = length;
		this.points = new ArrayList<Point>();
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setPointsOfShip(ArrayList<Point> points) {
		this.points = points;
	}
	
	public ArrayList<Point> getPointsOfShip() {
		return this.points;
	}
	
	public void decreaseHealth() {
		if (this.health > 0) { 
			this.health--;
		}
	}
}
