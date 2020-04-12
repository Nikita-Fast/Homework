package map;

import java.util.Scanner;

public class Game {
	Matrix myMap;
	Matrix enemyMap;
	final boolean isEnd = false;
	
	public Game() {
		myMap = new Matrix("Nikita", createFleet());
		enemyMap = new Matrix("Father", createFleet());
		
		String winnerName = enemyMap.getOwner();
		
		myMap.setFleet(myMap.getFleet());
	    enemyMap.setFleet(enemyMap.getFleet());
	    myMap.drawOpenedMap();    //подумай о методе рисующем обе карты в один ряд(горизонтальный)
		enemyMap.drawOpenedMap();
		
		
		while (!isEnd) {
			makeNextMove(myMap, enemyMap);
			if (enemyMap.areAllShipsDestroyed()) {
				winnerName = myMap.getOwner();
				break;
			}
			makeNextMove(enemyMap, myMap);
			if (myMap.areAllShipsDestroyed()) {
				winnerName = enemyMap.getOwner();
				break;
			}
		}
		System.out.println("Game over! Winner is " + winnerName); 
	}
	
	public Ship[] createFleet() {
		Ship[] fleet = new Ship[10];
	
		fleet[0] = new Ship(4);
		fleet[1] = new Ship(3);
		fleet[2] = new Ship(3);
		fleet[3] = new Ship(2);
		fleet[4] = new Ship(2);
		fleet[5] = new Ship(2);
		fleet[6] = new Ship(1); 
		fleet[7] = new Ship(1);
		fleet[8] = new Ship(1);
		fleet[9] = new Ship(1); 
		return fleet;
	}
	
	private void makeNextMove(Matrix attackerMap, Matrix attackedMap) {
		System.out.println(attackerMap.getOwner() + ", it's time to make a move!");
		Coord coord = readNewCoord(attackedMap);
		attackedMap.makeShot(coord);
		if (attackedMap.isHitTheShip(coord) && !attackedMap.areAllShipsDestroyed()) {
			do {
				coord = readNewCoordAndDoNotDrawMap(attackedMap);
				attackedMap.makeShot(coord);
			} while (attackedMap.isHitTheShip(coord) && !attackedMap.areAllShipsDestroyed());
		}
		System.out.println("------> Move goes to another player");
	}
	
	private Coord readNewCoord(Matrix map) {
		map.drawMap();
		int line = -1;
		int column = -1;
		Coord coord = new Coord(line, column);
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("choose coordinates: firstly line, then column (from 0 to 9)");
			line = in.nextInt();  
			column = in.nextInt();
			coord = new Coord(line, column);
		} while (!map.coordinatesChoseCorrectlyWithMessage(coord));
		//in.close();
		return coord;
	}
	
	private Coord readNewCoordAndDoNotDrawMap(Matrix map) {
		int line = -1;
		int column = -1;
		Coord coord = new Coord(line, column);
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("choose coordinates: firstly line, then column (from 0 to 9)");
			line = in.nextInt();  
			column = in.nextInt();
			coord = new Coord(line, column);
		} while (!map.coordinatesChoseCorrectlyWithMessage(coord));
		//in.close();
		return coord;
	}
}
