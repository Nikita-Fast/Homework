package map;

import java.awt.Color;
import java.util.Random;
import java.util.Scanner;

public class Game {
	Matrix myMap;
	Matrix enemyMap;
	final boolean isEnd = false;
	
	public Game() {
		MyFrame frame = new MyFrame();
		
		myMap = new Matrix("Nikita", createFleet());
		enemyMap = new Matrix("Bot", createFleet());
		
		frame.setMatrix(myMap, Players.first);
		frame.setMatrix(enemyMap, Players.second);
		
		String winnerName = enemyMap.getOwner();
		
		myMap.setFleet(myMap.getFleet());
	    enemyMap.setFleet(enemyMap.getFleet());
	    myMap.drawOpenedMap();    
	    enemyMap.drawOpenedMap();
		
	    frame.refreshCells();
	    
		while (!isEnd) {
			makeNextMove(myMap, enemyMap);
			if (enemyMap.areAllShipsDestroyed()) {
				winnerName = myMap.getOwner();
				myMap.drawOpenedMap();
				break;
			}
			//makeNextMove(enemyMap, myMap);
			randomBotMakeNextMove(enemyMap, myMap);
			frame.refreshCells();
			if (myMap.areAllShipsDestroyed()) {
				winnerName = enemyMap.getOwner();
				enemyMap.drawOpenedMap();
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
	
	private void randomBotMakeNextMove(Matrix attackerMap, Matrix attackedMap) {
		System.out.println(attackerMap.getOwner() + ", it's time to make a move!");
		Coord coord = botGivesRandomCoord(attackedMap);
		attackedMap.makeShot(coord);
		
		if (attackedMap.isHitTheShip(coord) && !attackedMap.areAllShipsDestroyed()) {
			do {
				coord = botGivesRandomCoordAndDoNotDrawMap(attackedMap);
				attackedMap.makeShot(coord);
			} while (attackedMap.isHitTheShip(coord) && !attackedMap.areAllShipsDestroyed());
		}
		System.out.println("------> Move goes to another player");
	}
	
	private Coord botGivesRandomCoord(Matrix map) {
		map.drawMap();
		int line = -1;
		int column = -1;
		Coord coord = new Coord(line, column);
		Random rnd = new Random();
		do {
			//System.out.println("choose coordinates: firstly line, then column (from 0 to 9)");
			line = rnd.nextInt(10);  
			column = rnd.nextInt(10);
			coord = new Coord(line, column);
		} while (!map.coordinatesChoseCorrectlyWithMessage(coord)); //отключи боту эти сообщения
		//in.close();
		return coord;
	}
	
	private Coord botGivesRandomCoordAndDoNotDrawMap(Matrix map) {
		int line = -1;
		int column = -1;
		Coord coord = new Coord(line, column);
		Random rnd = new Random();
		do {
			System.out.println("choose coordinates: firstly line, then column (from 0 to 9)");
			line = rnd.nextInt(10);  
			column = rnd.nextInt(10);
			coord = new Coord(line, column);
		} while (!map.coordinatesChoseCorrectlyWithMessage(coord));
		//in.close();
		return coord;
	}
	
	private Coord readNewCoordUseMouse(Matrix map) {
		
		return new Coord();
	}
	
	private Coord readNewCoordFirst(Matrix map) {
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
	
	private Coord readNewCoord(Matrix map) {
		map.drawMap();
		int line = -1;
		int column = -1;
		Coord coord = new Coord(line, column);
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("choose coordinates: firstly line, then column (from 0 to 9)");
			String input = in.nextLine();
			coord = getNewCoord(input);
		} while (!map.coordinatesChoseCorrectlyWithMessage(coord));
		//in.close();
		return coord;
	}
	
	private Coord readNewCoordAndDoNotDrawMapFirst(Matrix map) {
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
			String input = in.nextLine();
			coord = getNewCoord(input);
		} while (!map.coordinatesChoseCorrectlyWithMessage(coord));
		//in.close();
		return coord;
	}
	
	public static int getLineNumber(String in) {
		char[] letters = "abcdefghij".toCharArray();
		char[] input = in.toLowerCase().toCharArray();
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < letters.length; j++) {
				if (input[i] == letters[j]) {
					return j;
				}
			}
		}
		return -1;
	}
	
	public static int getPositionOfNumber(String in) {
		char[] numbers = "123456789".toCharArray();
		char[] input = in.toLowerCase().toCharArray();
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < numbers.length; j++) {
				if (input[i] == numbers[j]) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public static Coord getNewCoord(String in) {
		int line = getLineNumber(in);
		int column = getColumn(in);
		return new Coord(line, column);
	}
	
	public static int getColumn(String in) {
		int position = getPositionOfNumber(in);
		if (in.length() < position + 2) {
			char c = in.charAt(position);
			return Integer.parseInt(Character.toString(c)) - 1;
		}
		String str = in.substring(position, position + 2);
		return Integer.parseInt(str) - 1;
	}
}
