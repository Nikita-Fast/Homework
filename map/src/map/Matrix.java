package map;

import java.util.ArrayList;
import java.util.Random;

public class Matrix {
	private Cell[][] matrix;
	private String owner;
	
	public Matrix(String owner) {
		matrix = new Cell[10][10];
		this.owner = owner;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				matrix[i][j] = new Cell();
				matrix[i][j].setCell('.', i, j);
			}
		}
	}
	
	public void drawMap() {
		System.out.println(owner);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j].getState());
				System.out.print(' ');
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void setShips() {
		setShip(4);
		setShip(3);
		setShip(3);
		setShip(2);
		setShip(2);
		setShip(2);
		setShip(1);
		setShip(1);
		setShip(1);
		setShip(1);
	}
	
	private void setShip(int length) {
		boolean isVertical;
		int line;   //line and column are coordinates of top of ship, good idea is to create a class ship and add a special field called topOfShip 
		int column;
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
	
}
