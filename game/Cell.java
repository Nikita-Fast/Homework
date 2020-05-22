package game;

public class Cell {
	private boolean isOpen;
	private State state;
	private Ship ship;
	
	public Cell(boolean isOpen, State state) {
		this.isOpen = isOpen;
		this.state = state;
	}
	
	public void setIsOpenState(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	public void setState(State newState) {
		this.state = newState;
	}
	
	public boolean getIsOpenState() {
		return this.isOpen;
	}
	
	public State getState() {
		return this.state;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	public Ship getShip() {
		return this.ship;
	}
}
