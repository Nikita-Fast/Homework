package game;

import java.awt.event.MouseEvent;

public class Model {
	private Matrix gameMap;
	
	private Matrix player1Map;
	private Matrix player2Map;
	
	public Model() {
		this.player1Map = new Matrix();
		this.player2Map = new Matrix();
		
		this.gameMap = new Matrix();
	}
	
	public boolean madeSuccessfulShot(Point coord, Matrix associatedMap) {
		associatedMap.shootToSpecifiedPoint(coord);
		return associatedMap.shipWasHited(coord);	
	}
	
	public Matrix getPlayer1Map() {
		return this.player1Map;
	}
	public Matrix getPlayer2Map() {
		return this.player2Map;
	}
	
	public boolean shipWasPlacedSuccessfullyOnSpecifiedMap(Point coord, Ship probablyPlacedShip, Matrix map) {
		if (map.getCell(coord).getShip() == probablyPlacedShip) {
			return true;
		}
		return false;
	}
	
	public boolean shipWasPlacedSuccessfully(Point point) {
		if (this.gameMap.getCell(point).getState() == State.hasShip) {
			return true;
		}
		return false;
	}
	
	public Matrix getAccessToGameMap() {
		return this.gameMap;
	}
	
	public void removeShip(Point point) {
		if (point.isInRange()) {
			this.gameMap.removeShip(point);
		}
	}
	
	public void tryToPlaceShipOnSpecifiedMap(Point firstCoord, Point secondCoord, Ship ship, Matrix map) {
		if (firstCoord.isOnTheStraightLineWith(secondCoord)) {
			map.tryToPlaceShip(firstCoord, secondCoord, ship);
		}
	}
	
	public void tryToPlaceShip(Point firstCoord, Point secondPoint, Ship ship) {
		if (firstCoord.isOnTheStraightLineWith(secondPoint)) {
			this.gameMap.tryToPlaceShip(firstCoord, secondPoint, ship);
		}
	}
	
	public boolean pointIsGoodForFirstCoordOnSpecifiedMap(Point point, Matrix map) {
		if (map.noShipsAroundPoint(point)) {
			map.getCell(point).setState(State.special);
			return true;
		}
		return false;
	}
	
	public boolean pointIsAppropriateForFirstCoord(Point point) { 
		if (point.isInRange() && this.gameMap.noShipsAroundPoint(point)) {
			this.gameMap.getCell(point).setState(State.special);
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
	
	public void returnShipToSpecifiedMap(Ship ship, Matrix map) {
		map.returnShip(ship);
	}
	
	public void removeShipOnSpecifiedMap(Point coord, Matrix map) {
		map.removeShip(coord);
	}
	
	public void createFleetForSpecifiedMap(Matrix map) {
		map.createFleet();
	}
	
	public boolean fleetWasCompletelyPlacedOnSpecifiedMap(Matrix map) {
		if (map.fleetIsEmpty()) {
			return true;
		}
		return false;
	}
}
