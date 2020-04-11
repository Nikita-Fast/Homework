package map;

public class Cell {
	private char state;
	private int lineCoord;
	private int columnCoord;
	
	public void setCell(char state, int line, int column) {
		setState(state);
		setCoords(line, column);
	}
	
	public void setState(char state) {
		this.state = state;
	}
	
	public void setCoords(int line, int column) {
		lineCoord = line;
		columnCoord = column;
	}
	
	public char getState() {
		return state;
	}
}
