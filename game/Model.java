package game;


public class Model {
	private Matrix gameMap;
	
	public Model() {
		Matrix map = new Matrix();
		this.gameMap = map;
	}
	
	public Matrix getAccessToGameMap() {
		return this.gameMap;
	}
	
	public void tryToPlaceShip(Point firstCoord, Point secondPoint, int length) {
		if (firstCoord.isOnTheStraightLineWith(secondPoint)) {
			this.gameMap.tryToPlaceShip(firstCoord, secondPoint, length);
		}
	}
	
	public boolean pointIsAppropriateForFirstCoord(Point point) { 
		if (point.isInRange() && this.gameMap.noShipsAroundPoint(point)) {
			return true;
		}
		return false;
	}
	

}
