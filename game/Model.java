package game;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Model {
	
	private Matrix player1Map;
	private Matrix player2Map;
	
	
	private boolean nowIsPlayer1;
	private GameMode gameMode = GameMode.gameWithAnotherPlayer;
	private boolean firstCoordWasChoose;
	private Point firstCoord;
	private Ship currentShip;
	private boolean gameStarted = false;
	private boolean gameFinished = false;
	private boolean gameModeWasChoose = false;
	
	public Model() {
		this.player1Map = new Matrix();
		this.player2Map = new Matrix();
	}
	
	public boolean botMadeSuccessfulShot(Matrix map) {
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Point coord = generateRandomCoordForShot(map);
		map.shootToSpecifiedPoint(coord);
		return map.shipWasHited(coord);
	}
	
	
	public Point generateRandomCoordForShot(Matrix map) {
		Random random = new Random();
		int x;
		int y;
		Point coord;
		do {
			x = random.nextInt(10);
			y = random.nextInt(10);
			coord = new Point(x, y);
		} while (map.getCell(coord).getIsOpenState());
		return coord;
	}
	
	
	public void generateRandomFleetLocation(Matrix map) {
		map.describeFleet();
		removeAllShipsFromSpecifiedmap(map);
		map.describeFleet();
		System.out.println("return!!");
		for (Ship ship : map.getFleet()) {
			Ship currentShip = map.getShipOfSpecifiedLength(ship.getLength());
			if (currentShip.getLength() > 1) {
				Point firstCoord;
				Point secondCoord;
				do {
					firstCoord = generateAppropriateRandomFirstCoord(map);
					secondCoord = generateRandomSecondCoord(firstCoord, map);
					tryToPlaceShipOnSpecifiedMap(firstCoord, secondCoord, currentShip, map);
					if (!shipWasPlacedSuccessfullyOnSpecifiedMap(firstCoord, currentShip, map)) {
						map.getCell(firstCoord).setState(State.empty);
					}
				} while (!shipWasPlacedSuccessfullyOnSpecifiedMap(firstCoord, currentShip, map));
			}
			else {
				do {
					firstCoord = generateAppropriateRandomFirstCoord(map);
					map.tryToPlaceSmallShip(currentShip, firstCoord);
					if (!shipWasPlacedSuccessfullyOnSpecifiedMap(firstCoord, currentShip, map)) {
						map.getCell(firstCoord).setState(State.empty);
					}
				} while (!shipWasPlacedSuccessfullyOnSpecifiedMap(firstCoord, currentShip, map));
			}
		}
	}
	
	private void removeAllShipsFromSpecifiedmap(Matrix map) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Point point = new Point(i, j);
				if (map.getCell(point).getShip() != null) {
					map.removeShip(point);
				}
			}
		}
	}
	
	public Point generateAppropriateRandomFirstCoord(Matrix map) {
		Random random = new Random();
		int x;
		int y;
		Point coord;
		do {
			x = random.nextInt(10);
			y = random.nextInt(10);
			coord = new Point(x, y);
		} while (!pointIsGoodForFirstCoordOnSpecifiedMap(coord, map));
		return coord;
	}
	
	public Point generateRandomSecondCoord(Point firstCoord, Matrix map) {
		Random random = new Random();
		int x;
		int y;
		boolean i;
		Point coord;
		do {
			i = random.nextBoolean();
			if (i) {
				x = firstCoord.x;
				y = generateNumberNotEqualTo(firstCoord.y);
				coord = new Point(x, y);
			}
			else {
				x = generateNumberNotEqualTo(firstCoord.x);
				y = firstCoord.y;
				coord = new Point(x, y);
			}
		} while(!map.noShipsAroundPoint(coord));
		return coord;
	}
	
	public int generateNumberNotEqualTo(int x) {
		Random random = new Random();
		int y;
		do {
			y = random.nextInt(10);
		} while(y == x);
		return y;
	}
	
	
	public void setGameModeWasChoose(boolean gameModeWasChoose) {
		this.gameModeWasChoose = gameModeWasChoose;
	}
	
	public boolean gameModeWasChoose() {
		return this.gameModeWasChoose;
	}
	
	public void removeAllActionListenersFromSpecifiedButton(JButton button) {
		for (ActionListener listener : button.getActionListeners()) {
			button.removeActionListener(listener);
		}
	}
	
	public Point getCoordForLeftMap(MouseEvent e) {
		int x = (e.getX() - 83) / 31;
		int y = (e.getY() - 180) / 31;
		Point coord = new Point(x, y);
		return coord;
	}
	
	public Point getCoordForRightMap(MouseEvent e) {
		int x = (e.getX() - 600) / 31;
		int y = (e.getY() - 180) / 31;
		Point coord = new Point(x, y);
		return coord;
	}
	
	
	public void repaintSpecifiedMapDuringShipsLayout(JLabel[][] cellsOfMap, Matrix associatedMap) {
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				Cell cell = associatedMap.getCell(i, j);
				if (cell.getState() == State.empty) {
					cellsOfMap[i][j].setBackground(Color.white);
				}
				if (cell.getState() == State.hasShip) {
					cellsOfMap[i][j].setBackground(Color.green);
				}
				if (cell.getState() == State.special) {
					cellsOfMap[i][j].setBackground(Color.pink);
				}
			}
		}
	}
	
	
	public void repaintSpecifiedMapDuringGame(JLabel[][] cellsOfMap, Matrix associatedMap) {
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				Cell cell = associatedMap.getCell(i, j);
				if (!cell.getIsOpenState()) {
					cellsOfMap[i][j].setBackground(Color.white);
				}
				else {
					if (cell.getState() == State.hasShip) {
						cellsOfMap[i][j].setBackground(Color.orange);
					}
					if (cell.getState() == State.empty) {
						cellsOfMap[i][j].setBackground(Color.gray);
					}
				}
				
			}
		}
	}
	
	
	
	public void handleMouseClicks(MouseEvent e, Point coordWhereClicked, Matrix associatedMap, JButton[] shipButtons) {
		if (coordWhereClicked.isInRange()) {
			if (getCurrentShip() != null) {
				if (getCurrentShip().getLength() > 1) {
					if (e.getButton() == MouseEvent.BUTTON1 && firstCoordWasChoose()) {
						chooseSecondCoordOnSpecifiedMap(getFirstCoord(), coordWhereClicked, getCurrentShip(), associatedMap);
						setCurrentShip(null);
						//changeColorOfButtonsToGray();
						changeColorOfButtonsToGray(shipButtons);
					}
					else  {
						if (e.getButton() == MouseEvent.BUTTON1 && !firstCoordWasChoose()) {  
							chooseFirstCoordOnSpecifiedMap(coordWhereClicked, associatedMap);
						}
					}
				}
				
				if (getCurrentShip() != null && getCurrentShip().getLength() == 1) {
					if (!associatedMap.noShipsAroundPoint(coordWhereClicked)) {
						associatedMap.returnShip(getCurrentShip());
					}
					else {
						associatedMap.tryToPlaceSmallShip(getCurrentShip(), coordWhereClicked);
					}
					setCurrentShip(null);
					changeColorOfButtonsToGray(shipButtons);
				}
			}
			
			if (e.getButton() == MouseEvent.BUTTON3 && !firstCoordWasChoose()) {
				processMouseButton3Click(coordWhereClicked, associatedMap);
				changeColorOfButtonsToGray(shipButtons);
				
			}
		}
	}
	
	private void changeColorOfButtonsToGray(JButton[] buttons) {
		for (JButton button : buttons) {
			button.setBackground(Color.gray);
		}
	}
	
	
	private void chooseFirstCoordOnSpecifiedMap(Point point, Matrix map) {
		if (pointIsGoodForFirstCoordOnSpecifiedMap(point, map)) {
			setFirstCoord(point);
			setFirstCoordWasChoose(true);
		}
	}
	
	
	
	public void chooseSecondCoordOnSpecifiedMap(Point firstCoord, Point secondCoord, Ship ship, Matrix map) {
		if (secondCoord.isOnTheStraightLineWith(firstCoord) && !secondCoord.isEqualTo(firstCoord)) {
			tryToPlaceShipOnSpecifiedMap(firstCoord, secondCoord, ship, map);
			if (!shipWasPlacedSuccessfullyOnSpecifiedMap(firstCoord, ship, map)) {
				map.returnShip(ship);
				map.getCell(firstCoord).setState(State.empty);
			}
		}
		else {
			map.returnShip(ship);
			map.getCell(firstCoord).setState(State.empty);
		}
		setFirstCoord(null);
		setFirstCoordWasChoose(false);
		setCurrentShip(null);
	}
	
	
	
	public void processMouseButton3Click(Point coordWhereClicked, Matrix associatedMap) {
		returnShipToSpecifiedMap(getCurrentShip(), associatedMap);
		setCurrentShip(null);
		removeShipOnSpecifiedMap(coordWhereClicked, associatedMap);
	}
	
	public void processShipButtonClick(JButton button, Matrix map, int lengthOfShip) {
		shipButtonConnectedWithSpecifiedMapWasPressed(button, map);
		chooseShipOnSpecifiedMap(lengthOfShip, map, button);
	}
	
	public void shipButtonConnectedWithSpecifiedMapWasPressed(JButton button, Matrix map) {
		map.returnShip(this.currentShip);
		this.currentShip = null;
		button.setBackground(Color.cyan);
	}
	
	public void chooseShipOnSpecifiedMap(int length, Matrix map, JButton associatedButton) {
		if (map.thereIsShipOfSuchLength(length)) {
			this.currentShip = map.getShipOfSpecifiedLength(length);
		}
		else {
			System.out.println("choose another length!");
			associatedButton.setBackground(Color.gray);
		}
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
	
	public void tryToPlaceShipOnSpecifiedMap(Point firstCoord, Point secondCoord, Ship ship, Matrix map) {
		if (firstCoord.isOnTheStraightLineWith(secondCoord)) {
			map.tryToPlaceShip(firstCoord, secondCoord, ship);
		}
	}
	
	public boolean pointIsGoodForFirstCoordOnSpecifiedMap(Point point, Matrix map) {
		if (map.noShipsAroundPoint(point)) {
			map.getCell(point).setState(State.special);
			return true;
		}
		return false;
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
	
	public void setCurrentShip(Ship ship) {
		this.currentShip = ship;
	}
	
	public Ship getCurrentShip() {
		return this.currentShip;
	}
	
	public void setNowIsPlayer1(boolean nowIsPlayer1) {
		this.nowIsPlayer1 = nowIsPlayer1;
	}
	
	public boolean nowIsPlayer1() {
		return this.nowIsPlayer1;
	}
	
	public boolean firstCoordWasChoose() {
		return this.firstCoordWasChoose;
	}
	
	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}
	
	public boolean gameStarted() {
		return this.gameStarted;
	}
	
	public GameMode getGameMode() {
		return this.gameMode;
	}
	
	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}
	
	public boolean gameFinished() {
		return this.gameFinished;
	}
	
	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}
	
	public void setFirstCoord(Point firstCoord) {
		this.firstCoord = firstCoord;
	}
	
	public Point getFirstCoord() {
		return this.firstCoord;
	}
	
	public void setFirstCoordWasChoose(boolean firstCoordWasChoose) {
		this.firstCoordWasChoose = firstCoordWasChoose;
	}
}

