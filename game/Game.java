package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.text.View;

public class Game implements ActionListener{
	private GUI gui;
	private Model model;  
	
	private JButton startNewGameButton;
	private JButton[] shipButtons;
	private JButton continueButton;
	private JButton gameWithBotButton;
	private JButton gameWithAnotherPlayerButton;
	private JButton generateRandomShipLocationButton;
	
	public Game() {
		this.gui = new GUI();
		this.model = new Model();
		
		addNewGameButtonToGUI();
	}
	
	private void addGameModeSelectionButtons() {
		this.gameWithBotButton = gui.createGameModeSelectionButtons()[0];
		this.gameWithAnotherPlayerButton = gui.createGameModeSelectionButtons()[1];
		this.gameWithBotButton.addActionListener(this);
		this.gameWithAnotherPlayerButton.addActionListener(this);
		this.gui.getContentPane().add(this.gameWithBotButton);
		this.gui.getContentPane().add(this.gameWithAnotherPlayerButton);
		this.gui.repaint();
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.startNewGameButton) {
            this.gui.beginGameModeSelection();
            addGameModeSelectionButtons();
        }
        if (!model.gameModeWasChoose()) {
	        if (e.getSource() == this.gameWithBotButton) {
	        	this.model.setGameMode(GameMode.gameWithBot);
	        	this.model.setGameModeWasChoose(true);
	        	makePreparationsToStartShipPlacement();
	        }
	        if (e.getSource() == this.gameWithAnotherPlayerButton) {
	        	this.model.setGameMode(GameMode.gameWithAnotherPlayer);
	        	this.model.setGameModeWasChoose(true);
	        	makePreparationsToStartShipPlacement();
	        }
        }
        else {
        	if (this.model.nowIsPlayer1()) {
        		if (e.getSource() == this.generateRandomShipLocationButton) {
        			this.model.generateRandomFleetLocation(this.model.getPlayer1Map());
        			model.repaintSpecifiedMapDuringShipsLayout(gui.getPlayer1Cells(), model.getPlayer1Map());
        		}
		        if (!this.model.firstCoordWasChoose()) { 
		        	processShipButtonClick(e, this.model.getPlayer1Map());
		        }
		        if (e.getSource() == this.continueButton) {
		        	if (this.model.fleetWasCompletelyPlacedOnSpecifiedMap(this.model.getPlayer1Map())) {
		        		System.out.println("fleet was placed, we can go to next step!");
		        		this.model.setNowIsPlayer1(false);
		        		this.model.createFleetForSpecifiedMap(this.model.getPlayer2Map());
		        		if (model.getGameMode() == GameMode.gameWithAnotherPlayer) {
		        			this.gui.changeMapToPlayer2Map();
		        		}
		        		else {
		        			this.model.generateRandomFleetLocation(this.model.getPlayer2Map());
			        		this.model.setGameStarted(true);
		 	        		removeShipButtonsFromGUI();
		 	        		this.gui.enterGameMode();
		 	        		this.model.setNowIsPlayer1(true);
		        		}
		        	}
		        	else {
		        		System.out.println("finish placement of fleet before moving to next step");
		        	}
		        }
	        }
	        else {
	        	if (model.getGameMode() == GameMode.gameWithAnotherPlayer) {
	        		if (e.getSource() == this.generateRandomShipLocationButton) {
	        			this.model.generateRandomFleetLocation(this.model.getPlayer2Map());
	        			model.repaintSpecifiedMapDuringShipsLayout(gui.getPlayer2Cells(), model.getPlayer2Map());
	        		}
		        	 if (!this.model.firstCoordWasChoose()) {
		 	        	processShipButtonClick(e, this.model.getPlayer2Map());
		 	         }
		        	 if (e.getSource() == this.continueButton) {
		 	        	if (this.model.fleetWasCompletelyPlacedOnSpecifiedMap(this.model.getPlayer2Map())) {
		 	        		System.out.println("we can start the game!");
		 	        		
		 	        		this.model.setGameStarted(true);
		 	        		removeShipButtonsFromGUI();
		 	        		this.gui.enterGameMode();
		 	        		this.model.setNowIsPlayer1(true);
		 	        		
		 	        	}
		 	        	else {
		 	        		System.out.println("finish placement of fleet before moving to next step");
		 	        	}
		 	        }
	        	}
	        	/*
	        	else {
	        		this.model.generateRandomFleetLocation(this.model.getPlayer2Map());
	        		
	        		this.model.setGameStarted(true);
 	        		removeShipButtonsFromGUI();
 	        		this.gui.enterGameMode();
 	        		this.model.setNowIsPlayer1(true);
	        	}
	        	*/
	        }
        }
	}
	
	private void processShipButtonClick(ActionEvent e, Matrix associatedMap) {
		changeColorOfShipButtonsToGray();
		if (e.getSource() == this.shipButtons[0]) {
    		this.model.processShipButtonClick(this.shipButtons[0], associatedMap, 4);
    	}
    	if (e.getSource() == this.shipButtons[1]) {
    		this.model.processShipButtonClick(this.shipButtons[1], associatedMap, 3);
    	}
    	if (e.getSource() == this.shipButtons[2]) {
    		this.model.processShipButtonClick(this.shipButtons[2], associatedMap, 2);
    	}
    	if (e.getSource() == this.shipButtons[3]) {
    		this.model.processShipButtonClick(this.shipButtons[3], associatedMap, 1);
    	}
	}
	
	private void changeColorOfShipButtonsToGray() {
		for (JButton button : this.shipButtons) {
			button.setBackground(Color.gray);
		}
	}
	
	private void addMouseClickListener() {
		this.gui.getContentPane().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Point coord = model.getCoordForLeftMap(e);
				if (!model.gameStarted()) {	
					if (model.getGameMode() == GameMode.gameWithAnotherPlayer) {
						if (model.nowIsPlayer1()) {
							model.handleMouseClicks(e, coord, model.getPlayer1Map(), shipButtons);
							model.repaintSpecifiedMapDuringShipsLayout(gui.getPlayer1Cells(), model.getPlayer1Map());
						}
						else {
							model.handleMouseClicks(e, coord, model.getPlayer2Map(), shipButtons);
							model.repaintSpecifiedMapDuringShipsLayout(gui.getPlayer2Cells(), model.getPlayer2Map());
						}
					}
					else {
						if (model.nowIsPlayer1()) {
							model.handleMouseClicks(e, coord, model.getPlayer1Map(), shipButtons);
							model.repaintSpecifiedMapDuringShipsLayout(gui.getPlayer1Cells(), model.getPlayer1Map());
						}
					}
				}
				else {
					if (!model.gameFinished()) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (model.nowIsPlayer1()) {
								coord = model.getCoordForRightMap(e);
								if (model.getPlayer2Map().pointIsAppropriateForShot(coord)) {
									if (!model.madeSuccessfulShot(coord, model.getPlayer2Map())) {
										model.setNowIsPlayer1(false); //move go to second player
									}
									model.repaintSpecifiedMapDuringGame(gui.getPlayer2Cells(), model.getPlayer2Map());
									System.out.println("repaint");
								}
								//model.repaintSpecifiedMapDuringGame(gui.getPlayer2Cells(), model.getPlayer2Map());
								
								if (model.getPlayer2Map().allShipsWereDestroyed()) {
									model.setGameFinished(true);
									System.out.println("GAME FINISHED!!!");
								}
							}
							else {
								if (model.getGameMode() == GameMode.gameWithAnotherPlayer) {
									if (model.getPlayer1Map().pointIsAppropriateForShot(coord)) {
										if (!model.madeSuccessfulShot(coord, model.getPlayer1Map())) {
											model.setNowIsPlayer1(true); //move go to first player
										}
									}
									model.repaintSpecifiedMapDuringGame(gui.getPlayer1Cells(), model.getPlayer1Map());
									if (model.getPlayer1Map().allShipsWereDestroyed()) {
										model.setGameFinished(true);
										System.out.println("GAME FINISHED!!!");
									}
								}
								else {
									
								}
							}
							System.out.println("bot is going to make a shot!");
							if (!model.nowIsPlayer1() && !model.gameFinished() && model.getGameMode() == GameMode.gameWithBot) {
								if (!model.botMadeSuccessfulShot(model.getPlayer1Map())) {
									model.setNowIsPlayer1(true); //move go to first player
								}
								model.repaintSpecifiedMapDuringGame(gui.getPlayer1Cells(), model.getPlayer1Map());
								if (model.getPlayer1Map().allShipsWereDestroyed()) {
									model.setGameFinished(true);
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
	
	
	
	private void addNewGameButtonToGUI() {
		setUpNewGameButton();
		this.gui.addStartNewGameButtonToFrame(this.startNewGameButton);
	}
	
	private void setUpNewGameButton() {
		this.startNewGameButton = this.gui.createStartButton();
		this.startNewGameButton.addActionListener(this);
	}
	
	private void clearStartNewGameButton() {
		model.removeAllActionListenersFromSpecifiedButton(this.startNewGameButton);
		this.startNewGameButton = null;
	}
	
	private void clearGameModeSelectionButtons() {
		model.removeAllActionListenersFromSpecifiedButton(this.gameWithBotButton);
		model.removeAllActionListenersFromSpecifiedButton(this.gameWithAnotherPlayerButton);
		this.gameWithAnotherPlayerButton = null;
		this.gameWithBotButton = null;
	}
	
	private void addShipButtonsToGUI() {
		setUpShipButtons();
		addActionListenerToShipButtons();
		for (JButton button : this.shipButtons) {
			this.gui.getContentPane().add(button);
		}
	}
	
	private void setUpShipButtons() {
		this.shipButtons = new JButton[4];
		JButton[] shipButtons = this.gui.createShipButtons();
		for (int i = 0; i < 4; i ++) {
			this.shipButtons[i] = shipButtons[i];
		}
	}
	
	private void addActionListenerToShipButtons() {
		for (JButton button : this.shipButtons) {
			button.addActionListener(this);
		}
	}
	
	private void removeShipButtonsFromGUI() {
		for (JButton button : this.shipButtons) {
			model.removeAllActionListenersFromSpecifiedButton(button);
			this.gui.getContentPane().remove(button);
		}
		model.removeAllActionListenersFromSpecifiedButton(this.continueButton);
		this.gui.remove(this.continueButton);
		gui.repaint();
	}

	private void addContinueButtonsToGUI() {
		setUpContinueButton();
		this.continueButton.addActionListener(this);
		this.gui.getContentPane().add(this.continueButton);
	}
	
	private void setUpContinueButton() {
		this.continueButton = this.gui.createContinueButton();
	}
	
	private void setUpRandomShipLocationButton() {
		this.generateRandomShipLocationButton = this.gui.createRandomShipLocationButton();
	}
	
	private void addGenerateRandomShipLocationButton() {
		setUpRandomShipLocationButton();;
		this.generateRandomShipLocationButton.addActionListener(this);
		this.gui.getContentPane().add(this.generateRandomShipLocationButton);
	}
	
	private void makePreparationsToStartShipPlacement() {
		this.gui.uptadeFrame();
        clearStartNewGameButton();
        clearGameModeSelectionButtons();
        addMouseClickListener();
        addShipButtonsToGUI();
        addContinueButtonsToGUI();
        addGenerateRandomShipLocationButton();
        this.model.createFleetForSpecifiedMap(this.model.getPlayer1Map());
        this.model.setCurrentShip(null);
        this.model.setNowIsPlayer1(true);
	}
	
}
