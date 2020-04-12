package map;

import java.util.ArrayList;
import java.util.Random;

public class Matrix {
	private Cell[][] matrix;
	private String owner;
	private Ship[] fleet;
	
	public Matrix(String owner, Ship[] fleet) {
		matrix = new Cell[10][10];
		this.owner = owner;
		this.fleet = fleet;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				matrix[i][j] = new Cell();
				matrix[i][j].setCell('.', i, j);
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
		System.out.println("  0 1 2 3 4 5 6 7 8 9");
		for (int i = 0; i < matrix.length; i++) {
			System.out.print(i + "|");
			for (int j = 0; j < matrix[i].length; j++) {
				Cell cell = matrix[i][j];
				if (cell.getState() == '.' || cell.getState() == 'x') {
					System.out.print(' ');
					System.out.print('|');
				}
				else if (cell.getState() == 'o'){
					System.out.print('.');
					System.out.print('|');
				} else if (cell.getState() == '+') {
					System.out.print('x');
					System.out.print('|');
				}
			}
			System.out.println();
			//System.out.println("  ----------------------------");
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
		int line;   //line and column are coordinates of top of ship, good idea is to create a class ship and add a special field called topOfShip 
		int column;
		int length = ship.getLength();
		Random rnd = new Random();
		
		do {
			isVertical = rnd.nextBoolean();
			line = rnd.nextInt(10);
			column = rnd.nextInt(10);
		} while(spaceAroundShipIsFree(line, column, length, isVertical) != true); 
		/*
		ArrayList<Cell> list = getCellsAroundShip(line, column, length, isVertical);
		for (Cell singleCell : list) {
			singleCell.setState('7');
		} */
		
		drawShip(line, column, length, isVertical);
		addShipToAllHisCells(line, column, length, isVertical, ship);
		ship.setTopCoordsAndOrientation(line, column, isVertical);
	}
	
	private void addShipToAllHisCells(int line, int column, int length, boolean isVertical, Ship ship) {
		if (isVertical == true) {
			for (int i = 0; i < length; i++) {
				matrix[line + i][column].setShip(ship);
			}
		}
		else {
			for (int i = 0; i < length; i++) {
				matrix[line][column + i].setShip(ship);
			}
		}
	}
	
	private void drawShip(int line, int column, int length, boolean isVertical) {
		if (isVertical == true) {
			for (int i = 0; i < length; i++) {
				matrix[line + i][column].setState('x');
			}
		}
		else {
			for (int i = 0; i < length; i++) {
				matrix[line][column + i].setState('x');
			}
		}
	}
	
	private boolean shipIsInRange(int line, int column, int length, boolean isVertical) {
		boolean isInRange = false;
		if (line >= 0 && line <= 9 && column >= 0 && column <= 9) {
			if (isVertical == true) {
				if (line + length - 1 <= 9) {
					isInRange = true;
				}
			}
			else if (column + length - 1 <= 9) {
				isInRange = true;
			}
		}
		return isInRange;
	}
	
	
	public  ArrayList<Cell> getCellsAroundShip(int line, int column, int length, boolean isVertical) {
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
	
	public boolean spaceAroundShipIsFree(int line, int column, int length, boolean isVertical) {
		boolean spaceIsFree = true;
		if (shipIsInRange(line, column, length, isVertical) == false) { //this place is really bad!
			return false;
		}
		ArrayList<Cell> cellsAround = getCellsAroundShip(line, column, length, isVertical);
		for (Cell singleCell : cellsAround) {
			if (singleCell.getState() == 'x') {
				spaceIsFree = false;
			}
		}
		return spaceIsFree;
	}

	
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
	}
}
