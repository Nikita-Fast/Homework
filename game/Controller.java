package game;

import java.awt.Color;
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
	
	private JButton startNewGameButton;
	private JButton ship4Button;
	private JButton ship3Button;
	private JButton ship2Button;
	private JButton ship1Button;
	private JButton[] shipButtons;
	
	private boolean firstCoordWasChoose;
	private Point firstCoord;
	
	private boolean lengthWasNotChoose;

	private int lengthOfShip;
	private Ship currentShip;
	
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
            this.lengthWasNotChoose = true;
            this.model.getAccessToGameMap().createFleet();
        }
        if ( !firstCoordWasChoose) {
        	if (e.getSource() == this.shipButtons[0]) {
        		shipButtonWasPressed(this.shipButtons[0]);
        		this.lengthOfShip = 4;
        		chooseShip(this.lengthOfShip);
        	}
        	if (e.getSource() == this.shipButtons[1]) {
        		shipButtonWasPressed(this.shipButtons[1]);
        		this.lengthOfShip = 3;
        		chooseShip(this.lengthOfShip);
        	}
        	if (e.getSource() == this.shipButtons[2]) {
        		shipButtonWasPressed(this.shipButtons[2]);
        		this.lengthOfShip = 2;
        		chooseShip(this.lengthOfShip);
        	}
        	if (e.getSource() == this.shipButtons[3]) {
        		shipButtonWasPressed(this.shipButtons[3]);
        		this.lengthOfShip = 1;
        		chooseShip(this.lengthOfShip);
        	}
        }  
	}
	
	private void shipButtonWasPressed(JButton button) {
		changeColorOfButtonsToGray();
		this.model.getAccessToGameMap().returnShip(this.currentShip);
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
	
	private void chooseShip(int length) {
		if (this.model.getAccessToGameMap().thereIsShipOfSuchLength(length)) {
			this.currentShip = model.getAccessToGameMap().getShipOfSpecifiedLength(length);
			this.lengthWasNotChoose = false;
			System.out.println("ship was chose");
		}
		else {
			System.out.println("choose another length!");
		}
	}
	
	private void addMouseClickListenerToView() {
		this.view.getContentPane().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = (e.getX() - 83) / 31;
				int y = (e.getY() - 180) / 31;
				if (lengthOfShip != 1) {
					if (coordinatesAreInRange(x, y) && lengthOfShip != 0) {
						if(e.getButton() == MouseEvent.BUTTON1 && firstCoordWasChoose == true) {
							tryToDefineDirectionAndPlaceShip(x, y, firstCoord, currentShip);
						}
						else  {
							if (e.getButton() == MouseEvent.BUTTON1 && firstCoordWasChoose == false && lengthWasNotChoose == false) {  
								tryToChooseFirstCoord(x, y);
							}
						}
					}
				}
				if (lengthOfShip == 1) {
					model.getAccessToGameMap().tryToPlaceSmallShip(currentShip, new Point(x, y));
					
					lengthOfShip = 0;
					lengthWasNotChoose = true;
					firstCoord = null;
					firstCoordWasChoose = false;
				}
				if (e.getButton() == MouseEvent.BUTTON3) {
					//model.clear
					firstCoord = null;
					firstCoordWasChoose = false;
				}
				model.getAccessToGameMap().drawMatrix();
				repaintMap();
			}
		});
	}
	
	private void tryToDefineDirectionAndPlaceShip(int x, int y, Point firstCoord, Ship ship) {
		Point secondPoint = new Point(x, y);
		if (secondPoint.isOnTheStraightLineWith(firstCoord) && !secondPoint.isEqualTo(firstCoord)) {
			tryToPlaceShip(firstCoord, secondPoint, ship);
		}
		this.lengthOfShip = 0;
		this.lengthWasNotChoose = true;
		for (JButton button : this.shipButtons) {
			button.setBackground(Color.gray);
		}
		firstCoord = null;
		firstCoordWasChoose = false;
		repaintMap();
		
		this.currentShip = null;
		
		//this.model.getAccessToGameMap().drawMatrix();
	}
	
	private void tryToPlaceShip(Point firstCoord, Point secondPoint, Ship ship) {
		model.tryToPlaceShip(firstCoord, secondPoint, ship);
	}
	
	private boolean coordinatesAreInRange(int x, int y) {
		if (0 <= x && x <= 9 && 0 <= y && y <= 9) {
			return true;
		}
		return false;
	}
	
	private void tryToChooseFirstCoord(int x, int y) {
		Point point = new Point(x, y);
		if (model.pointIsAppropriateForFirstCoord(point)) {
			firstCoord = point;
			firstCoordWasChoose = true;
			
			JLabel cell = view.getCell(x, y);
			if  (cell != null) {
				cell.setBackground(Color.CYAN);
			}
		}
	}
	
	private void repaintMap() {
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				if (this.model.getAccessToGameMap().getCell(i, j).getState() == State.special) {
					this.view.getCell(j, i).setBackground(Color.pink);
				}
				if (this.model.getAccessToGameMap().getCell(i, j).getState() == State.hasShip) {
					this.view.getCell(j, i).setBackground(Color.green);
				}
				if (this.model.getAccessToGameMap().getCell(i, j).getState() == State.empty) {
					this.view.getCell(j, i).setBackground(Color.white);
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
			view.getContentPane().add(button);
			view.repaint();
		}
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
	
}
