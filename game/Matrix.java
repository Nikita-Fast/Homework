package game;

import java.util.ArrayList;

public class Matrix {
	private Cell[][] matrix;
	private Ship[] fleet;
	private int numberOfDestroyedShips;
	
	public Matrix() {
		matrix = new Cell[10][10];
		fleet = new Ship[10];
		numberOfDestroyedShips = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = new Cell(false, State.empty);
			}
		}
	}
	
	public void describeFleet() {
		int a = 0, b = 0, c = 0, d = 0;
		for (Ship ship : fleet) {
			if (ship != null) {
				if (ship.getLength() == 1) {
					a++;
				}
				if (ship.getLength() == 2) {
					b++;
				}
				if (ship.getLength() == 3) {
					c++;
				}
				if (ship.getLength() == 4) {
					d++;
				}
			}
		}
		System.out.println("len 1: "+ a + "   len 2: " + b + "   len 3: " + c + "   len 4: " + d);
	}
	
	public void drawMatrix() {
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[i].length; j++) {
				if (this.matrix[i][j].getState() == State.empty) {
					System.out.print(". ");
				}
				if (this.matrix[i][j].getState() == State.hasShip) {
					System.out.print("+ ");
				}
			}
			System.out.println();
		}
	}
	
	public void removeShip(Point point) {
		if (point.isInRange()) {
			Ship ship = getCell(point).getShip();
			if (ship != null) {
				changeStateOfCellsToEmpty(ship.getPointsOfShip());
				returnShip(ship);
			}
		}
	}
	
	private void changeStateOfCellsToEmpty(ArrayList<Point> points) {
		if (!points.isEmpty()) {
			for (Point p : points) {
				getCell(p).setState(State.empty);
			}
		}
	}
	
	public void tryToPlaceShip(Point p1, Point p2, Ship ship) { 
		ArrayList<Point> points = getPointsForShip(p1, p2, ship.getLength());
		if (positionForShipIsGood(points)) {
			placeShip(points, ship);
		}
	}
	
	private void placeShip(ArrayList<Point> points, Ship ship) {
		changeStateOfCells(points);
		for (Point point : points) {
			getCell(point).setShip(ship);
		}
		ship.setPointsOfShip(points);
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
	
	public void tryToPlaceSmallShip(Ship ship, Point coord) {
		if (noShipsAroundPoint(coord)) {
			ArrayList<Point> list = new ArrayList<Point>();
			list.add(coord);
			placeShip(list, ship);
		}
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
	
	private ArrayList<Cell> getCellsAroundShipWithShip(Ship ship) {
		ArrayList<Cell> cellsAround = new ArrayList<Cell>();
		for (Point point : ship.getPointsOfShip()) {
			cellsAround.addAll(getCellsAroundPointIncludingPoint(point));
		}
		return cellsAround;
	}
	
	private boolean allCellsInListAreEmpty(ArrayList<Cell> cellsAround) {
		for (Cell cell : cellsAround) {     //���� �� ����� ������ ��� ��� � ���� ����� ������� ����������� ������
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
	
	public boolean thereIsShipOfSuchLength(int length) {
		for (Ship ship : this.fleet) {
			if (ship != null && ship.getLength() == length) {
				return true;
			}
		}
		return false;
	}
	
	public void returnShip(Ship ship) {
		if (ship != null) {
			for (int i = 0; i < this.fleet.length; i++) {
				if (this.fleet[i] == null) {
					this.fleet[i] = ship;
					i = this.fleet.length;  //stop cycle, this is just quick solution in order to prevent bug
				}
			}
		}
	}
	
	public Ship getShipOfSpecifiedLength(int length) {
		for (int i = 0; i < this.fleet.length; i++) {
			if (this.fleet[i] != null) {
				if (this.fleet[i].getLength() == length) {
					Ship ship = this.fleet[i];
					this.fleet[i] = null;
					return ship;
				}
			}
		}
		return null;
	}
	
	public void createFleet() {
		int k = 0;
		for (int length = 1; length <= 4; length++) {
			for (int amount = 5 - length; amount > 0; amount--) {
				this.fleet[k] = new Ship(length);
				k++;
			}
		}
		System.out.println("final value: " + k);
	}
	
	public boolean fleetIsEmpty() {
		for (Ship ship : this.fleet) {
			if (ship != null) {
				return false;
			}
		}
		return true;
	}
	
	public boolean pointIsAppropriateForShot(Point coord) {
		if (coord.isInRange()) {
			if (!getCell(coord).getIsOpenState()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean madeSuccessfulShot(Point coord) {
		if (pointIsAppropriateForShot(coord)) {
			shootToSpecifiedPoint(coord);
			return shipWasHited(coord);
		}
		return false;
	}
	
	public void shootToSpecifiedPoint(Point point) {
		getCell(point).setIsOpenState(true);
		if (getCell(point).getState() == State.hasShip) {
			getCell(point).getShip().decreaseHealth();
			if (getCell(point).getShip().getHealth() == 0) {
				destroyShip(getCell(point).getShip());
			}
		}
	}
	
	public void destroyShip(Ship ship) {
		for (Cell cell : getCellsAroundShipWithShip(ship)) {
			cell.setIsOpenState(true);
		}
		this.numberOfDestroyedShips++;
	}
	
	public boolean shipWasHited(Point coord) {
		if (getCell(coord).getState() == State.hasShip) {
			return true;
		}
		return false;
	}
	
	public boolean allShipsWereDestroyed() {
		if (this.numberOfDestroyedShips == 10) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
