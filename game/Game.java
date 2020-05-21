package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Game {
	
	public Game() {
		new Controller();
		System.out.println("123");
	}
	/*
	public void placeShips(Matrix matrix) {
		//выбрать длину корабля (спец кномка на фрейме)
		//проверить, что такие корабли еще остались
		//выбрать первую точку (нужен обработчик нажатий мыши
		//выбираем вторую точку и по ней строим направление корабля
		//проверяем что так можно поставить корабль
		//меняем состояние у нужных ячеек
		//рисуем это
		//переходим к новому кораблю
		
		final int length = chooseLengthOfShip();
		do {
			//length = chooseLengthOfShip();
		} while (isPossibleToPlaceShipOfSuchLength(length, matrix) == false);
		
		gameInterface.getLeftPanel().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
				if (e.getButton() == MouseEvent.BUTTON1 && point1.isEqualTo(Point.SPECIAL_POINT) == true) {
					int x = e.getX();
					int y = e.getY();
					if (0 < x && x < 311 && 0 < y && y < 311) {
						x = (x - 1) / 31;
						y = (y - 1) / 31;
					}
					point1 = new Point(x, y);
					System.out.println("but 1 :" + x + " " + y);
				}
				
				if (e.getButton() == MouseEvent.BUTTON1 && point1.isEqualTo(Point.SPECIAL_POINT) == false) { //значит первая точка уже выбрана
					int x = e.getX();
					int y = e.getY();
					if (0 < x && x < 311 && 0 < y && y < 311) {
						x = (x - 1) / 31;
						y = (y - 1) / 31;
					}
					Point point2 = new Point(x, y);

					if (point1.isEqualTo(point2) == false) { //не нажали в point1
						if (twoPointsAreOnSameLine(point1, point2) == true) {
							
							ArrayList<Cell> list = getCellsForShip(point1, point2, length, matrix); //собрать в список все ячейки где будет стоять корабль //проверить клетки которые в листе и вокруг него на наличие корабля
							if (list.isEmpty() == false) {
								for (Cell cell : list) {
									cell.setState(State.hasShip); //поставили корабль. теперь надо его создать и добавить во флот
								}
								point1 = Point.SPECIAL_POINT;
								System.out.println("butt 2 success");
								gameInterface.drawLeftPanel();
							}
						}
					}
					
						
				}
			}
		});
	}
	
	
	
	
	private ArrayList<Cell> getCellsForShip(Point top, Point p2, int length, Matrix matrix) { //важен порядок ввода, первая точка это вершина
		ArrayList<Cell> listOfCells = new ArrayList<Cell>();
		Orientation orientation = determineOrientation(top, p2);
		
		boolean isVertical;
		Point vertex;
		ArrayList<Cell> cellsAround = new ArrayList<Cell>();
		
		if (orientation == Orientation.up) {
			isVertical = true;
			vertex = p2;
			if (top.y - (length - 1) >= 0) {
				for (int i = 0; i <= length - 1; i++) {
					Cell cell = matrix.getCell(top.y - i, top.x);
					listOfCells.add(cell);
					cellsAround = getCellsAroundFutureShip(vertex.y, vertex.x, length, isVertical, matrix);
				}
			}
		}
		if (orientation == Orientation.down) {
			isVertical = true;
			vertex = top;
			if (top.y + (length - 1) <= 9) {
				for (int i = 0; i <= length - 1; i++) {
					Cell cell = matrix.getCell(top.y + i, top.x);
					listOfCells.add(cell);
					cellsAround = getCellsAroundFutureShip(vertex.y, vertex.x, length, isVertical, matrix);
				}
			}
		}
		if (orientation == Orientation.right) {
			isVertical = false;
			vertex = top;
			if (top.x + (length - 1) <= 9) {
				for (int i = 0; i <= length - 1; i++) {
					Cell cell = matrix.getCell(top.y, top.x + i);
					listOfCells.add(cell);
					cellsAround = getCellsAroundFutureShip(vertex.y, vertex.x, length, isVertical, matrix);
				}
			}
		}
		if (orientation == Orientation.left) {
			isVertical = false;
			vertex = p2;
			if (top.x - (length - 1) >= 0) {
				for (int i = 0; i <= length - 1; i++) {
					Cell cell = matrix.getCell(top.y, top.x - i);
					listOfCells.add(cell);
					cellsAround = getCellsAroundFutureShip(vertex.y, vertex.x, length, isVertical, matrix);
				}
			}
		}
		
		if (isPossibleToPlaceShipHere(cellsAround) == true) {
			return listOfCells;
		}
		
		return null;
	}
	
	private boolean isPossibleToPlaceShipHere(ArrayList<Cell> cellsAround) {
		boolean isPossible = true;
		if (cellsAround.isEmpty() == false) {
			for (Cell cell : cellsAround) {
				if (cell.getState() == State.hasShip) {
					isPossible = false;
				}
			}
			return isPossible;
		}
		else {
			return false;
		}
	}
	
	public  ArrayList<Cell> getCellsAroundFutureShip(int line, int column, int length, boolean isVertical, Matrix matrix) {
		ArrayList<Cell> cellsAround = new ArrayList<Cell>();
		
		if (isVertical) {
			for (int i = line - 1; i <= line + length; i++) { 
				for (int j = column - 1 ; j <= column + 1; j++) {
					if (0 <= i && i <= 9 && 0 <= j && j <= 9) {
						cellsAround.add(matrix.getCell(i, j));
						
					}
				}
			}
		}
		else {
			for (int i = line - 1; i <= line + 1; i++) { 
				for (int j = column - 1 ; j <= column + length; j++) {
					if (0 <= i && i <= 9 && 0 <= j && j <= 9) {
						cellsAround.add(matrix.getCell(i, j));
					}
				}
			}
		}
		return cellsAround;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private Orientation determineOrientation(Point top, Point p2) {
		Orientation orientation = Orientation.initial;
		if (top.x == p2.x) {
			if (top.y < p2.y) {
				orientation = Orientation.up;
			}
			else {
				orientation = Orientation.down;
			}
		}
		if (top.y == p2.y) {
			if (top.x > p2.x) {
				orientation = Orientation.left;
			}
			else {
				orientation = Orientation.right;
			}
		}
		return orientation;
	}
	
	private boolean twoPointsAreOnSameLine(Point p1, Point p2) {
		if (p1.isEqualTo(p2) == false) {
			if (p1.x == p2.x || p1.y == p2.y) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isPossibleToPlaceShipOfSuchLength(int length, Matrix matrix) {
		boolean isPossible = true;
		int count = 0;
		if (matrix.fleetIsEmpty() == false) {
			for (Ship ship : matrix.getFleet()) {
				if (ship.getLength() == length) {
					count++;
				}
			}
			if (count == 5 - length) {
				isPossible = false;
			}
		}
		return isPossible;
	}
	
	public int chooseLengthOfShip() {
		return 3;
	}
	
	private Point chooseBearingPoint() {
		Point point = new Point(0, 0);
		//point = choosePointUsingMouse(player1); //на левом поле
		return point;
	} */
}
