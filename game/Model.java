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
	
	public void tryToPlaceShip(Point firstCoord, Point secondPoint, Ship ship) {
		if (firstCoord.isOnTheStraightLineWith(secondPoint)) {
			this.gameMap.tryToPlaceShip(firstCoord, secondPoint, ship);
		}
	}
	
	public boolean pointIsAppropriateForFirstCoord(Point point) { 
		if (point.isInRange() && this.gameMap.noShipsAroundPoint(point)) {
			return true;
		}
		return false;
	}
	
	public Ship getShipOfLenght(int length) {
		if (this.gameMap.thereIsShipOfSuchLength(length)) {
			return this.gameMap.getShipOfSpecifiedLength(length);
		}
		return null;
	}

}
