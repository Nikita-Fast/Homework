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
		for (int i = 0; i < SIZE; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < matrix.length; i++) {
			System.out.print(i + "|");
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
		for (int i = 0; i < SIZE; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		for (int i = 0; i < matrix.length; i++) {
			System.out.print(i + "|");
			for (int j = 0; j < matrix[i].length; j++) {
				Cell cell = matrix[i][j];
				if (cell.getState() == State.empty) {
					System.out.print(".|");
				}
				else if (cell.getState() == State.hasShip){
					System.out.print("x|");			
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
		} while(positionIsGood(line, column, isVertical, length) != true); 
		
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

	
	
	
	
	/*
	public Cell getCell(int line, int column) {
		return matrix[line][column];
	}
	
	private ArrayList<Cell> getCellsAroundShipWithoutShip(Ship ship) { //без клеточек, которые занимает сам корабль
		ArrayList<Cell> cellsAround = new ArrayList<Cell>();
		boolean isVertical = ship.isVertical();
		int line = ship.getTopLine();
		int column = ship.getTopColumn();
		int length = ship.getLength();
		
		if (isVertical) {
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
			cell.setState('o');
		}
		System.out.println("SHIP WAS KILLED!");
	}
	
	public void makeShoot(int line, int column) {
		Cell cell = getCell(line, column);
		if (cell.getState() == '.') {
			cell.setState('o');
		}
		else {
			cell.setState('+');
			cell.getShip().decreaseHealth();
			if (cell.getShip().getHealth() == 0) {
				destroyShip(cell.getShip());
			}
		}
	}
	
	public boolean isHit(int line, int column) {
		if (matrix[line][column].getState() == 'x') {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isAllShipsDestroyed() {
		//System.out.println("check for does all ship destroyed");
		for (Ship singleShip : fleet) {
			if (singleShip.getHealth() > 0) {
				//System.out.println("No");
				return false;
			}
		}
		//System.out.println("Yes");
		return true;
	} */
}
