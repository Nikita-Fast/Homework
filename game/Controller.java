package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Controller implements ActionListener{
	private View view;
	private Model model;
	
	private boolean nowIsPlayer1;
	private GameMode gameMode = GameMode.gameWithAnotherPlayer;  //+++++++++++++++++++++++++++++++              needed to be set
	
	private JButton startNewGameButton;
	private JButton[] shipButtons;
	private JButton continueButton;
	
	private boolean firstCoordWasChoose;
	private Point firstCoord;
	
	private Ship currentShip;
	
	private boolean gameStarted = false;
	private boolean gameFinished = false;
	
	public Controller() {
		this.view = new View();
		this.model = new Model();
		
		addNewGameButtonToView();
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.startNewGameButton) {
            System.out.println("new game will start immediately!");
            this.view.uptadeFrame();
            clearStartNewGameButton();
            addMouseClickListenerToView();
            addShipButtonsToView();
            addContinueButtonsToView();
            this.model.createFleetForSpecifiedMap(this.model.getPlayer1Map());
            this.currentShip = null;
            this.nowIsPlayer1 = true;                                             //*****************************
    
        }
        if (this.nowIsPlayer1) {
	        if (!firstCoordWasChoose) {
	        	processShipButtonClick(e, this.model.getPlayer1Map());
	        }
	        if (e.getSource() == this.continueButton) {
	        	if (this.model.fleetWasCompletelyPlacedOnSpecifiedMap(this.model.getPlayer1Map())) {
	        		System.out.println("fleet was placed, we can go to next step!");
	        		this.view.changeMapToPlayer2Map();
	        		this.nowIsPlayer1 = false;
	        		this.model.createFleetForSpecifiedMap(this.model.getPlayer2Map());
	        	}
	        	else {
	        		System.out.println("finish placement of fleet before moving to next step");
	        	}
	        }
        }
        else {
        	 if (!firstCoordWasChoose) {
 	        	processShipButtonClick(e, this.model.getPlayer2Map());
 	         }
        	 if (e.getSource() == this.continueButton) {
 	        	if (this.model.fleetWasCompletelyPlacedOnSpecifiedMap(this.model.getPlayer2Map())) {
 	        		System.out.println("we can start the game!");
 	        		
 	        		this.gameStarted = true;
 	        		removeButtonsFromView();
 	        		this.view.enterGameMode();
 	        		this.nowIsPlayer1 = true;
 	        		//repaintViewToGameMode();
 	        		
 	        	}
 	        	else {
 	        		System.out.println("finish placement of fleet before moving to next step");
 	        	}
 	        }
        }
	}
	
	private void processShipButtonClick(ActionEvent e, Matrix associatedMap) {
		if (e.getSource() == this.shipButtons[0]) {
    		processButtonClick(this.shipButtons[0], associatedMap, 4);
    	}
    	if (e.getSource() == this.shipButtons[1]) {
    		processButtonClick(this.shipButtons[1], associatedMap, 3);
    	}
    	if (e.getSource() == this.shipButtons[2]) {
    		processButtonClick(this.shipButtons[2], associatedMap, 2);
    	}
    	if (e.getSource() == this.shipButtons[3]) {
    		processButtonClick(this.shipButtons[3], associatedMap, 1);
    	}
	}
	
	private void processButtonClick(JButton button, Matrix map, int lengthOfShip) {
		shipButtonConnectedWithSpecifiedMapWasPressed(button, map);
		chooseShipOnSpecifiedMap(lengthOfShip, map);
	}
	
	private void shipButtonConnectedWithSpecifiedMapWasPressed(JButton button, Matrix map) {
		changeColorOfButtonsToGray();
		map.returnShip(this.currentShip);
		this.currentShip = null;
		changeColorOfButtonToCyan(button);
	}
	
	private void changeColorOfButtonsToGray() {
		for (JButton button : this.shipButtons) {
			button.setBackground(Color.gray);
		}
	}
	
	private void changeColorOfButtonToCyan(JButton button) {
		button.setBackground(Color.cyan);
	}
	
	private void chooseShipOnSpecifiedMap(int length, Matrix map) {
		if (map.thereIsShipOfSuchLength(length)) {
			this.currentShip = map.getShipOfSpecifiedLength(length);
		}
		else {
			System.out.println("choose another length!");
			changeColorOfButtonsToGray();
		}
	}
	
	private void addMouseClickListenerToView() {
		this.view.getContentPane().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = (e.getX() - 83) / 31;
				int y = (e.getY() - 180) / 31;
				Point coord = new Point(x, y);
				if (!gameStarted) {	
					if (gameMode == GameMode.gameWithAnotherPlayer) {
						if (nowIsPlayer1) {
							handleMouseClicks(e, coord, model.getPlayer1Map());
							repaintPlayer1Map();
						}
						else {
							handleMouseClicks(e, coord, model.getPlayer2Map());
							repaintPlayer2Map();
						}
					}
					else {
						/*
						handleMouseClicks(e, coord, model.getPlayer1Map());
						repaintPlayer1Map();
						//model.fillMapRandomly(model.getPlayer2Map()); */
					}
				}
				else {
					if (!gameFinished) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (nowIsPlayer1) {
								x = (e.getX() - 600) / 31;
								y = (e.getY() - 180) / 31;
								coord = new Point(x, y);
								if (coord.isInRange()) {
									System.out.println("coord is in range!!!");
								}
								if (model.getPlayer2Map().pointIsAppropriateForShot(coord)) {
									if (!model.madeSuccessfulShot(coord, model.getPlayer2Map())) {
										nowIsPlayer1 = false; //move go to second player
									}
								}
								repaintMapsWhilePlaying();
								if (model.getPlayer2Map().allShipsWereDestroyed()) {
									gameFinished = true;
									System.out.println("GAME FINISHED!!!");
								}
							}
							else {
								if (model.getPlayer1Map().pointIsAppropriateForShot(coord)) {
									if (!model.madeSuccessfulShot(coord, model.getPlayer1Map())) {
										nowIsPlayer1 = true; //move go to first player
									}
								}
								repaintMapsWhilePlaying();
								if (model.getPlayer1Map().allShipsWereDestroyed()) {
									gameFinished = true;
									System.out.println("GAME FINISHED!!!");
								}
							}
						}
					}
					else {
					}
				}
			}
		});
	}
	
	private void handleMouseClicks(MouseEvent e, Point coordWhereClicked, Matrix associatedMap) {
		if (coordWhereClicked.isInRange()) {
			if (currentShip != null) {
				if (currentShip.getLength() > 1) {
					if (e.getButton() == MouseEvent.BUTTON1 && firstCoordWasChoose == true) {
						chooseSecondCoordOnSpecifiedMap(firstCoord, coordWhereClicked, currentShip, associatedMap);
						currentShip = null;
						changeColorOfButtonsToGray();
					}
					else  {
						if (e.getButton() == MouseEvent.BUTTON1 && firstCoordWasChoose == false) {  
							chooseFirstCoordOnSpecifiedMap(coordWhereClicked, associatedMap);
						}
					}
				}
				
				if (currentShip != null && currentShip.getLength() == 1) {
					if (!associatedMap.noShipsAroundPoint(coordWhereClicked)) {
						associatedMap.returnShip(currentShip);
					}
					else {
						associatedMap.tryToPlaceSmallShip(currentShip, coordWhereClicked);
					}
					currentShip = null;
					changeColorOfButtonsToGray();
				}
			}
			
			if (e.getButton() == MouseEvent.BUTTON3 && !firstCoordWasChoose) {
				processMouseButton3Click(coordWhereClicked, associatedMap);
			}
		}
	}
	
	private void processMouseButton3Click(Point coordWhereClicked, Matrix associatedMap) {
		changeColorOfButtonsToGray();
		model.returnShipToSpecifiedMap(this.currentShip, associatedMap);
		currentShip = null;
		model.removeShipOnSpecifiedMap(coordWhereClicked, associatedMap);
	}
	
	
	private void chooseSecondCoordOnSpecifiedMap(Point firstCoord, Point secondCoord, Ship ship, Matrix map) {
		if (secondCoord.isOnTheStraightLineWith(firstCoord) && !secondCoord.isEqualTo(firstCoord)) {
			this.model.tryToPlaceShipOnSpecifiedMap(firstCoord, secondCoord, ship, map);
			if (!model.shipWasPlacedSuccessfullyOnSpecifiedMap(firstCoord, ship, map)) {
				map.returnShip(ship);
				map.getCell(firstCoord).setState(State.empty);
			}
		}
		else {
			map.returnShip(ship);
			map.getCell(firstCoord).setState(State.empty);
		}
		this.firstCoord = null;
		this.firstCoordWasChoose = false;
		this.currentShip = null;
	}
	
	private boolean coordinatesAreInRange(int x, int y) {
		if (0 <= x && x <= 9 && 0 <= y && y <= 9) {
			return true;
		}
		return false;
	}
	
	private void chooseFirstCoordOnSpecifiedMap(Point point, Matrix map) {
		if (this.model.pointIsGoodForFirstCoordOnSpecifiedMap(point, map)) {
			firstCoord = point;
			firstCoordWasChoose = true;
		}
	}
	
	private void repaintPlayer1Map() {
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				Cell cell = this.model.getPlayer1Map().getCell(i, j);
				if (cell.getState() == State.empty) {
					this.view.changeColorOfCell(Color.white, this.view.getSpecifiedPlayer1Cell(i, j));
				}
				if (cell.getState() == State.hasShip) {
					this.view.changeColorOfCell(Color.green, this.view.getSpecifiedPlayer1Cell(i, j));
				}
				if (cell.getState() == State.special) {
					this.view.changeColorOfCell(Color.pink, this.view.getSpecifiedPlayer1Cell(i, j));
				}
			}
		}
	}
	
	private void repaintPlayer2Map() {
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				Cell cell = this.model.getPlayer2Map().getCell(i, j);
				if (cell.getState() == State.empty) {
					this.view.changeColorOfCell(Color.white, this.view.getSpecifiedPlayer2Cell(i, j));
				}
				if (cell.getState() == State.hasShip) {
					this.view.changeColorOfCell(Color.green, this.view.getSpecifiedPlayer2Cell(i, j));
				}
				if (cell.getState() == State.special) {
					this.view.changeColorOfCell(Color.pink, this.view.getSpecifiedPlayer2Cell(i, j));
				}
			}
		}
	}
	
	private void repaintMapsWhilePlaying() {
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				Cell cell = this.model.getPlayer1Map().getCell(i, j);
				if (!cell.getIsOpenState()) {
					this.view.changeColorOfCell(Color.white, this.view.getSpecifiedPlayer1Cell(i, j));
				}
				else {
					if (cell.getState() == State.hasShip) {
						this.view.changeColorOfCell(Color.orange, this.view.getSpecifiedPlayer1Cell(i, j));
					}
					if (cell.getState() == State.empty) {
						this.view.changeColorOfCell(Color.gray, this.view.getSpecifiedPlayer1Cell(i, j));
					}
				}
				cell = this.model.getPlayer2Map().getCell(i, j);
				if (!cell.getIsOpenState()) {
					this.view.changeColorOfCell(Color.white, this.view.getSpecifiedPlayer2Cell(i, j));
				}
				else {
					if (cell.getState() == State.hasShip) {
						this.view.changeColorOfCell(Color.orange, this.view.getSpecifiedPlayer2Cell(i, j));
					}
					if (cell.getState() == State.empty) {
						this.view.changeColorOfCell(Color.gray, this.view.getSpecifiedPlayer2Cell(i, j));
					}
				}
			}
		}
	}
	
	private void addNewGameButtonToView() {
		setUpNewGameButton();
		this.view.addStartNewGameButtonToFrame(this.startNewGameButton);
	}
	
	private void setUpNewGameButton() {
		this.startNewGameButton = this.view.addStartButtonVersion2();
		this.startNewGameButton.addActionListener(this);
	}
	
	private void clearStartNewGameButton() {
		this.startNewGameButton.removeActionListener(this);
		this.startNewGameButton = null;
	}
	
	private void addShipButtonsToView() {
		setUpShipButtons();
		addActionListenerToShipButtons();
		for (JButton button : this.shipButtons) {
			this.view.getContentPane().add(button);
			this.view.repaint();
		}
	}
	
	private void removeButtonsFromView() {
		for (JButton button : this.shipButtons) {
			button.removeActionListener(this);
			this.view.getContentPane().remove(button);
		}
		this.continueButton.removeActionListener(this);
		this.view.remove(this.continueButton);
		view.repaint();
	}
   
	private void addActionListenerToShipButtons() {
		for (JButton button : this.shipButtons) {
			button.addActionListener(this);
		}
	}
	
	private void setUpShipButtons() {
		this.shipButtons = new JButton[4];
		for (int i = 0; i < 4; i ++) {
			JButton button = new JButton();
			button.setSize(124 - 31 * i, 31);
			button.setLocation(450 + 31 * i, 212 + 60 * i);
			button.setBackground(Color.gray);
			this.shipButtons[i] = button;
		}
		
	}
	
	private void addContinueButtonsToView() {
		setUpContinueButton();
		this.continueButton.addActionListener(this);
		this.view.getContentPane().add(this.continueButton);
	}
	
	private void setUpContinueButton() {
		this.continueButton = new JButton();
		this.continueButton.setSize(124, 31);
		this.continueButton.setLocation(450, 212 + 240);
		this.continueButton.setBackground(Color.white);
		this.continueButton.setText("Next step");
		this.continueButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
	}
	
	
	
}
