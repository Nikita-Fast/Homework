package game;

import java.util.ArrayList;

public class Matrix {
	private Cell[][] matrix;
	private Ship[] fleet;
	
	public Matrix() {
		matrix = new Cell[10][10];
		fleet = new Ship[10];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = new Cell(false, State.empty);
			}
		}
	}
	
	public void tryToPlaceShip(Point p1, Point p2, int length) { 
		ArrayList<Point> points = getPointsForShip(p1, p2, length);
		if (positionForShipIsGood(points)) {
			placeShip(points);
		}
	}
	
	private void placeShip(ArrayList<Point> points) {
		changeStateOfCells(points);
	}
	
	private boolean positionForShipIsGood(ArrayList<Point> points) {
		if (allPointsAreInRange(points) && thereAreNoShipsNear(points)) {
			return true;
		}
		return false;
	}
	
	private boolean allPointsAreInRange(ArrayList<Point> points) {
		for (Point point : points) {
			if (!point.isInRange()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean thereAreNoShipsNear(ArrayList<Point> points) {
		for (Point point : points) {
			if (!noShipsAroundPoint(point)) {
				return false;
			}
		}
		return true;
	}
	
	private void changeStateOfCells(ArrayList<Point> points) {
		for (Point point : points) {
			getCell(point).setState(State.hasShip);  //CHANGE TO HASSHIP!!!!!!!!!!!!!!!
		}
	}
	
	private void printCoordinatesOfPoints(ArrayList<Point> points) {
		for (Point point : points) {
			System.out.println("print method: " + point.x + "  " + point.y);
		}
		System.out.println("------------");
	}
	
	private ArrayList<Point> getPointsForShip(Point p1, Point p2, int length) {
		ArrayList<Point> points = new ArrayList<Point>();
		
		if (p1.isInTheColumnWith(p2)) {
			if (p1.y < p2.y) {
				System.out.println("in the column");
				for (int i = p1.y; i < p1.y + length; i++) {
					points.add(new Point(p1.x, i));
				}
			}
			else {
				System.out.println("in the line");
				System.out.println("anime");
				for (int i = p1.y; i > p1.y - length; i--) {
					points.add(new Point(p1.x, i));
				}
			}
		}
		
		if (p1.isInTheLineWith(p2)) {
			if (p1.x < p2.x) {
				for (int i = p1.x; i < p1.x + length; i++) {
					points.add(new Point(i, p1.y));
				}
			}
			else {
				for (int i = p1.x; i > p1.x - length; i--) {
					points.add(new Point(i, p1.y));
				}
			}
		}
		return points;
	}
	
	
	public boolean noShipsAroundPoint(Point point) {
		if (point.isInRange()) {
			if (allCellsInListAreEmpty(getCellsAroundPointIncludingPoint(point))) {
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Cell> getCellsAroundPointIncludingPoint(Point point) {
		ArrayList<Cell> cellsAround = new ArrayList<Cell>();
		for (int i = point.y - 1; i <= point.y + 1; i++) {
			for (int j = point.x - 1; j <= point.x + 1; j++) {
				Point newPoint = new Point(j, i);
				if (newPoint.isInRange()) {
					System.out.println(newPoint.x + "  " + newPoint.y);
					cellsAround.add(this.matrix[i][j]);
				}
			}
		}
		return cellsAround;
	}
	
	private boolean allCellsInListAreEmpty(ArrayList<Cell> cellsAround) {
		for (Cell cell : cellsAround) {     //лист не будет пустым так как в него будех входить центральна€ €чейка
			if (cell.getState() == State.hasShip) {
				return false;
			}
		}
		return true;
	}
	
	public Cell getCell(int line, int column) {
		return matrix[line][column];
	}
	
	public Cell getCell(Point p) {
		return matrix[p.y][p.x];  //check this method
	}
	
	public Ship[] getFleet() {
		return this.fleet;
	}
	
	public boolean fleetIsEmpty() {
		for (Ship ship : this.fleet) {
			if (ship != null) {
				return false;
			}
		}
		return true;
	}
	
}
