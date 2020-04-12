package map;

import java.util.Scanner;

public class Game {
	Matrix myMap;
	Matrix enemyMap;
	boolean isEnd = false;
	
	public Game() {
		enemyMap = new Matrix("Father", createFleet());
		myMap = new Matrix("Nikita", createFleet());
		
		String winnerName = enemyMap.getOwner();
		
		myMap.setFleet(myMap.getFleet());
		myMap.drawMap();
	    enemyMap.setFleet(enemyMap.getFleet());
		enemyMap.drawMap();
		while (!isEnd) {
			isEnd = makeMove(myMap, enemyMap);
			if (isEnd) {
				winnerName = myMap.getOwner();
				break;
			}
			isEnd = makeMove(enemyMap, myMap);
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
	
	private boolean makeMove(Matrix yourMatrix, Matrix enemyMatrix) {
		boolean allShipsDestroyed = false;
		enemyMatrix.drawMap();
		System.out.println(yourMatrix.getOwner() + ", it's time to make move!");
		System.out.println("choose coordinates: firstly line, then column (from 0 to 9)");
		Scanner in = new Scanner(System.in);
		int line = in.nextInt();  
		int column = in.nextInt();
		
		while (enemyMatrix.isHit(line, column)) {
			enemyMatrix.makeShoot(line, column);
			System.out.println(yourMatrix.getOwner() + " made his move!");
			enemyMatrix.drawMap();
			if (enemyMatrix.isAllShipsDestroyed()) {
				System.out.println("-------------------------------------");
				allShipsDestroyed = true;
				break;
			}
			System.out.println(yourMatrix.getOwner() + ", it's time to make move!");
			System.out.println("choose coordinates: firstly line, then column (from 0 to 9)");
			line = in.nextInt();  //обработать дерьмовый ввод (неправильный)
			column = in.nextInt();
		}
		if (!allShipsDestroyed) {
			enemyMatrix.makeShoot(line, column);
			System.out.println(yourMatrix.getOwner() + " made his move!");
			enemyMatrix.drawMap();
			System.out.println("----> Move goes to another player!");
		}
		return allShipsDestroyed;
	}
	
	private void printShipsHealth(Matrix matrix) {
		Ship[] fleet = matrix.getFleet();
		System.out.println("Ships health: ");
		for (Ship ship : fleet) {
			System.out.print(ship.getHealth() + " ");
		}
		System.out.println();
	}
}
