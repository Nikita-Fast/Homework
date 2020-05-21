package game;

public class Ship {
	
	private int length;
	private int health;
	
	public Ship(int length) {
		this.length = length;
		health = length;
	}
	
	public int getLength() {
		return length;
	}
}
