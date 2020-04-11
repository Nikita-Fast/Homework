package map;

public class Game {
	Matrix myMap;
	Matrix enemyMap;
	
	public Game() {
		myMap = new Matrix("Nikita");
		enemyMap = new Matrix("Enemy");
		myMap.setShips(); 
		enemyMap.setShips();
		myMap.drawMap();
		enemyMap.drawMap();
	}
}
