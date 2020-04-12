package map;

public class Ship { //координаты вершины, ориентация и длина нужны чтобы получить все клетки вокруг (отметить их после уничтожения)
	private int length;
	private int health;
	private Coord top;
	private boolean isVertical;
	
	public Ship(int length) {
		this.length = length;
		health = length;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getHealth() {
		return health;
	}
	
	public Coord getTop() {
		return top;
	}
	
	public boolean getIsVertical() {
		return isVertical;
	}
	
	public void decreaseHealth() {
		health--;
	}
	
	public void setTopAndOrientation(Coord top, boolean isVertical) {
		this.top = top;
		this.isVertical = isVertical;
	}
}
