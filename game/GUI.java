package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame {
	
	private JButton startNewGameButton;
	
	private JLabel[][] player1cells;
	private JLabel[][] player2cells;
	private JPanel player1Panel;
	private JPanel player2Panel;
	
	public void enterGameMode() {
		//getContentPane().remove(this.player2Panel);
		getContentPane().removeAll();
		addPlayer2Map(); //можно ставить новую, а не прошлую, так за рисование она не отвечает
		this.player2Panel.setLocation(600, 180);
		addPlayer1Map();  //можно ставить новую, а не прошлую, так за рисование она не отвечает
	}
	
	public void changeMapToPlayer2Map() {
		getContentPane().remove(this.player1Panel);
		addPlayer2Map();
		repaint();
	}
	
	public JLabel[][] getPlayer1Cells() {
		return this.player1cells;
	}
	
	public JLabel[][] getPlayer2Cells() {
		return this.player2cells;
	}
	

	public GUI() {
		basicInitialisation();
	}
	
	public void uptadeFrame() {
		clearFrame();
		addPlayer1Map();
		repaint();
	}
	
	public void beginGameModeSelection() {
		clearFrame();
		
	}
	
	
	
	private void basicInitialisation() {
		initStartFrame();
	}
	
	private void initStartFrame() {
		setVisible(true);
		setBounds(200, 200, 1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
	}
	
	public void addStartNewGameButtonToFrame(JButton button) {
		this.startNewGameButton = button;
		getContentPane().add(this.startNewGameButton, BorderLayout.CENTER);
	}
	
	public JButton createStartButton() {
		JButton button = new JButton();
		button.setSize(400, 250);
		button.setFont(new Font("Tahoma", Font.PLAIN, 26));
		button.setText("Click to start a new game");
		return button;
	}
	
	private void addPlayer1Map() {
		createPlayer1Map();
		getContentPane().add(this.player1Panel);
	}
	
	private void addPlayer2Map() {
		createPlayer2Map();
		getContentPane().add(this.player2Panel);
	}
	
	
	private void createPlayer1Map() {
		JPanel panel = new JPanel();
		panel.setBounds(83, 180, 311, 311);
		panel.setBackground(Color.black);
		panel.setLayout(null);
		this.player1Panel = panel;
		initPlayer1Map();
	}
	
	private void createPlayer2Map() {
		JPanel panel = new JPanel();
		panel.setBounds(83, 180, 311, 311);
		panel.setBackground(Color.black);
		panel.setLayout(null);
		this.player2Panel = panel;
		initPlayer2Map();
	}
	
	private void initPlayer1Map() {
		this.player1cells = new JLabel[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				JLabel cell = new JLabel();
				cell.setBackground(Color.white);
				cell.setOpaque(true);
				cell.setBounds(31*j + 1, 31*i + 1, 30, 30);
				this.player1cells[i][j] = cell;
				this.player1Panel.add(cell);
			}
		}
	}
	
	private void initPlayer2Map() {
		this.player2cells = new JLabel[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				JLabel cell = new JLabel();
				cell.setBackground(Color.white);
				cell.setOpaque(true);
				cell.setBounds(31*j + 1, 31*i + 1, 30, 30);
				this.player2cells[i][j] = cell;
				this.player2Panel.add(cell);
			}
		}
	}
	
	
	private void clearFrame() {
		getContentPane().removeAll();
		getContentPane().setLayout(null);
	}
	
	public JButton[] createShipButtons() {
		JButton[] shipButtons = new JButton[4];
		for (int i = 0; i < 4; i++) {
			JButton button = new JButton();
			button.setSize(124 - 31 * i, 31);
			button.setLocation(450 + 31 * i, 212 + 60 * i);
			button.setBackground(Color.gray);
			shipButtons[i] = button;
		}
		return shipButtons;
	}
	
	public JButton createContinueButton() {
		JButton continueButton = new JButton();
		continueButton.setSize(124, 31);
		continueButton.setLocation(450, 212 + 240);
		continueButton.setBackground(Color.white);
		continueButton.setText("Next step");
		continueButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		return continueButton;
	}
	
	
	public JButton[] createGameModeSelectionButtons() {
		JButton[] modeSelectionButtons = new JButton[2];
		for (int i = 0; i < 2; i++) {
			JButton button = new JButton();
			button.setSize(350, 150);
			button.setLocation(100 + i * 450, 250);
			button.setBackground(Color.white);
			if (i == 0) {
				button.setText("Game with bot");
			}
			else {
				button.setText("Game with another player");
			}
			button.setFont(new Font("Tahoma", Font.PLAIN, 24));
			modeSelectionButtons[i] = button;
		}
		return modeSelectionButtons;
	}
	
	public JButton createRandomShipLocationButton() {
		JButton button = new JButton();
		button.setSize(200, 62);
		button.setLocation(450 + 124 + 50, 212);
		button.setBackground(Color.white);
		button.setText("Random layout");
		button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		return button;
	}
	
}
