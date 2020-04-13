package map;

import java.util.ArrayList;
import java.util.Random;

public class Matrix {
	private Cell[][] matrix;
	private Ship[] fleet;
	private String owner;
	private static final int SIZE = 10;
	
	public Matrix(String owner, Ship[] fleet) {
		matrix = new Cell[SIZE][SIZE];
		this.owner = owner;
		this.fleet = fleet;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = new Cell();
				matrix[i][j].setDefault(State.empty, false);
			}
		}
	}
	
	public String getOwner() {
		return owner;
	}
	
	public Ship[] getFleet() {
		return fleet;
	}
	
	public void drawMap() {
		System.out.println(owner + "'s map");
		System.out.print("  ");
		for (int i = 1; i <= SIZE; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < matrix.length; i++) {
			System.out.print((char)('A' + i) + "|");
			for (int j = 0; j < matrix[i].length; j++) {
				Cell cell = matrix[i][j];
				if (cell.getIsOpenedState() == false) {
					System.out.print(" |");
				}
				else {
					if (cell.getState() == State.empty) {
						System.out.print(".|");
					}
					else if (cell.getState() == State.hasShip){
						System.out.print("x|");
					}
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
	
	
	public void drawOpenedMap() {
		System.out.println(owner + "'s map");
		System.out.print("  ");
		for (int i = 1; i <= SIZE; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < matrix.length; i++) {
			System.out.print((char)('A' + i) + "|");
			for (int j = 0; j < matrix[i].length; j++) {
				Cell cell = matrix[i][j];
				if (cell.getIsOpenedState() == false) {
					
					if (cell.getState() == State.empty) {
						System.out.print(" |");
					}
					else if (cell.getState() == State.hasShip){
						System.out.print("+|");
					}
				}
				else {
					if (cell.getState() == State.empty) {
						System.out.print(".|");
					}
					else if (cell.getState() == State.hasShip){
						System.out.print("x|");
					}
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void setFleet(Ship[] fleet) {
		for (Ship ship : fleet) {
			setShip(ship);
		}
	}
	
	public void setShip(Ship ship) {
		boolean isVertical;
		int line;   
		int column;
		int length = ship.getLength();
		Random rnd = new Random();
		
		do {
			isVertical = rnd.nextBoolean();
			line = rnd.nextInt(SIZE);
			column = rnd.nextInt(SIZE);
		} while (positionIsGood(line, column, isVertical, length) != true); 
		
		Coord top = new Coord(line, column);
		ship.setTopAndOrientation(top, isVertical); 
		drawShip(ship);  
		addShipToAllHisCells(ship);  
	}
	
	private void addShipToAllHisCells(Ship ship) {
		if (ship.getIsVertical() == true) {
			for (int i = 0; i < ship.getLength(); i++) {
				matrix[ship.getTop().line + i][ship.getTop().column].setShip(ship);
			}
		}
		else {
			for (int i = 0; i < ship.getLength(); i++) {
				matrix[ship.getTop().line][ship.getTop().column + i].setShip(ship);
			}
		}
	}
	
	private void drawShip(Ship ship) {
		if (ship.getIsVertical() == true) {
			for (int i = 0; i < ship.getLength(); i++) {
				matrix[ship.getTop().line + i][ship.getTop().column].setState(State.hasShip);;
			}
		}
		else {
			for (int i = 0; i < ship.getLength(); i++) {
				matrix[ship.getTop().line][ship.getTop().column + i].setState(State.hasShip);
			}
		}
	}
	
	private boolean positionIsGood(int line, int column, boolean isVertical, int length) {
		if (shipWillBeInRange(line, column, length, isVertical)) {
			return spaceForFutureShipIsFree(line, column, length, isVertical);
		}
		return false;
	}
	
	private boolean shipWillBeInRange(int line, int column, int length, boolean isVertical) {
		boolean willBeInRange = false;
		if (line >= 0 && line <= 9 && column >= 0 && column <= 9) {
			if (isVertical == true) {
				if (line + length - 1 <= 9) {
					willBeInRange = true;
				}
			}
			else if (column + length - 1 <= 9) {
				willBeInRange = true;
			}
		}
		return willBeInRange;
	}
	
	public  ArrayList<Cell> getCellsAroundFutureShip(int line, int column, int length, boolean isVertical) {
		ArrayList<Cell> cellsAround = new ArrayList<Cell>();
		
		if (isVertical) {
			for (int i = line - 1; i <= line + length; i++) { 
				for (int j = column - 1 ; j <= column + 1; j++) {
					if (0 <= i && i <= 9 && 0 <= j && j <= 9) {
						cellsAround.add(matrix[i][j]);
					}
				}
			}
		}
		else {
			for (int i = line - 1; i <= line + 1; i++) { 
				for (int j = column - 1 ; j <= column + length; j++) {
					if (0 <= i && i <= 9 && 0 <= j && j <= 9) {
						cellsAround.add(matrix[i][j]);
					}
				}
			}
		}
		return cellsAround;
	}
	
	public boolean spaceForFutureShipIsFree(int line, int column, int length, boolean isVertical) {
		boolean spaceIsFree = true;
		
		ArrayList<Cell> cellsAround = getCellsAroundFutureShip(line, column, length, isVertical);
		for (Cell cell : cellsAround) {
			if (cell.getState() == State.hasShip) {
				spaceIsFree = false;
				return spaceIsFree;
			}
		}
		return spaceIsFree;
	}

	public Cell getCell(Coord coord) {
		return matrix[coord.line][coord.column];
	} 
	
	private ArrayList<Cell> getCellsAroundShipWithoutShip(Ship ship) { //без клеточек, которые занимает сам корабль
		ArrayList<Cell> cellsAround = new ArrayList<Cell>();
		int line = ship.getTop().line;
		int column = ship.getTop().column;
		int length = ship.getLength();
		
		if (ship.getIsVertical()) {
			for (int i = line - 1; i <= line + length; i++) { 
				for (int j = column - 1 ; j <= column + 1; j++) {
					if (0 <= i && i <= 9 && 0 <= j && j <= 9) {
						if (matrix[i][j].getShip() != ship) {
							cellsAround.add(matrix[i][j]);
						}
					}
				}
			}
		}
		else {
			for (int i = line - 1; i <= line + 1; i++) { 
				for (int j = column - 1 ; j <= column + length; j++) {
					if (0 <= i && i <= 9 && 0 <= j && j <= 9) {
						if (matrix[i][j].getShip() != ship) {
							cellsAround.add(matrix[i][j]);
						}
					}
				}
			}
		}
		
		return cellsAround;
	}
	
	private void destroyShip(Ship ship) {
		ArrayList<Cell> cellsAround = getCellsAroundShipWithoutShip(ship);
		for (Cell cell : cellsAround) {
			cell.setIsOpenedState(true);  //открыть все эти клетки
		}
		System.out.println("The attacker KILLED " + owner + "'s ship!");
	}
	
	public void makeShot(Coord coord) { //проверить что координаты подходят по диапозону и что это не открытая ячейка
		Cell cell = getCell(coord);
		if (cell.getState() == State.empty) {
			cell.setIsOpenedState(true);
			System.out.println("The attacker didn't hit " + owner + "'s ships!");
		}
		else {
			cell.setIsOpenedState(true);
			cell.getShip().decreaseHealth();
			System.out.println("The attacker hit " + owner + "'s ship!");
			if (cell.getShip().getHealth() == 0) {
				destroyShip(cell.getShip());
			}
		}
		drawMap();
	}
	
	public boolean isHitTheShip(Coord coord) {
		if (matrix[coord.line][coord.column].getState() == State.hasShip) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean areAllShipsDestroyed() {
		for (Ship singleShip : fleet) {
			if (singleShip.getHealth() > 0) {
				return false;
			}
		}
		return true;
	} 
	
	public boolean coordinatesChoseCorrectly(Coord coord) {
		if (coordinatesAreInRange(coord)) {
			return thisCellIsClosed(coord);
		}
		return false;
	}
	
	public boolean coordinatesAreInRange(Coord coord) {
		return 0 <= coord.line && coord.line <= SIZE - 1 && 
				0 <=coord.column && coord.column <= SIZE - 1;
	}
	
	public boolean thisCellIsClosed(Coord coord) {
		return !getCell(coord).getIsOpenedState();
	}
	
	public boolean coordinatesChoseCorrectlyWithMessage(Coord coord) {
		if (!coordinatesChoseCorrectly(coord)) {
			System.out.println("Specify the correct coordinates!");
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
