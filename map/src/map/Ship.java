package map;

public class Ship { //координаты вершины, ориентация и длина нужны чтобы получить все клетки вокруг (отметить их после уничтожения)
	private int length;
	private int health;
	private int topline;
	private int topColumn;
	private boolean isVertical;
	
	public int getLength() {
		return length;
	}
	
	public int getTopLine() {
		return topline;
	}
	
	public int getTopColumn() {
		return topColumn;
	}
	
	public boolean isVertical() {
		return isVertical;
	}
	
	public Ship(int length) {
		this.length = length;
		health = length;
	}
	
	public void decreaseHealth() {
		health--;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setTopCoordsAndOrientation(int line, int column, boolean isVertical) {
		topline = line;
		topColumn = column;
		this.isVertical = isVertical;
	}
}
