package map;

public class Cell {
	private State state;
	private boolean isOpened;
	private Ship ship;
	
	public void setDefault(State state, boolean isOpened) {
		setState(state);
		setIsOpenedState(isOpened);
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	public void setIsOpenedState(boolean isOpened) {
		this.isOpened = isOpened;
	}
	
	public boolean getIsOpenedState() {
		return isOpened;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	public Ship getShip() {
		return ship;
	}
}
