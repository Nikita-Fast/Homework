package game;

public class Cell {
	private boolean isOpen;
	private State state;
	
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
}
