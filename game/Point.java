package game;

public class Point {
	public int x;
	public int y;
	public static final Point SPECIAL_POINT = new Point(10, 10);
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isInTheColumnWith(Point anothePoint) {
		if (this.x == anothePoint.x) {
			return true;
		}
		return false;
	}
	
	public boolean isInTheLineWith(Point anothePoint) {
		if (this.y == anothePoint.y) {
			return true;
		}
		return false;
	}
	
	public boolean isOnTheStraightLineWith(Point anothePoint) {
		if (this.x == anothePoint.x || this.y == anothePoint.y) {
			return true;
		}
		return false;
	}
	
	public boolean isEqualTo(Point p) {
		if (this.x == p.x && this.y == p.y) {
			return true;
		}
		return false;
	}
	
	public boolean isInRange() {
		if (0 <= this.x && this.x <= 9 && 0 <= this.y && this.y <= 9) {
			return true;
		}
		return false;
	}
}
