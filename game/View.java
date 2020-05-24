package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View extends JFrame {
	
	private JLabel[][] cells;
	private JButton setShip;
	private JButton startNewGameButton;
	private JPanel panel;
	
	private JLabel[][] player1cells;
	private JLabel[][] player2cells;
	private JPanel player1Panel;
	private JPanel player2Panel;
	
	public void enterGameMode() {
		getContentPane().remove(this.player2Panel);
		addPlayer2Map(); //можно ставить новую, а не прошлую, так за рисование она не отвечает
		this.player2Panel.setLocation(600, 180);
		addPlayer1Map();  //можно ставить новую, а не прошлую, так за рисование она не отвечает
	}
	
	public void changeMapToPlayer2Map() {
		getContentPane().remove(this.player1Panel);
		addPlayer2Map();
		repaint();
	}
	
	public JLabel getSpecifiedPlayer1Cell(int x, int y) {
		return this.player1cells[x][y];
	}
	
	public JLabel getSpecifiedPlayer2Cell(int x, int y) {
		return this.player2cells[x][y];
	}
	
	public void changeColorOfCell(Color color, JLabel cell) {
		cell.setBackground(color);
	}
	
	public View() {
		basicInitialisation();
	}
	
	public void uptadeFrame() {
		clearFrame();
		addPlayer1Map();
		repaint();
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
	
	private void addStartButton() {
		JButton button = new JButton();
		button.setSize(400, 250);
		button.setFont(new Font("Tahoma", Font.PLAIN, 26));
		button.setText("Click to start a new game");
		//button.addActionListener(this);
		this.startNewGameButton = button;
		getContentPane().add(this.startNewGameButton, BorderLayout.CENTER);
		//this.startNewGameButton.addActionListener(this);
	}
	
	public JButton addStartButtonVersion2() {
		JButton button = new JButton();
		button.setSize(400, 250);
		button.setFont(new Font("Tahoma", Font.PLAIN, 26));
		button.setText("Click to start a new game");
		return button;
	}
	
	private void addMap() {
		createMap();
		getContentPane().add(this.panel);
	}
	
	private void addPlayer1Map() {
		createPlayer1Map();
		getContentPane().add(this.player1Panel);
	}
	
	private void addPlayer2Map() {
		createPlayer2Map();
		getContentPane().add(this.player2Panel);
	}
	
	private void createMap() {
		JPanel panel = new JPanel();
		panel.setBounds(83, 180, 311, 311);
		panel.setBackground(Color.black);
		panel.setLayout(null);
		this.panel = panel;
		
		initMap();
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
				if ((i + j) % 3 == 0) {
					cell.setBackground(Color.red);
				}
				cell.setOpaque(true);
				cell.setBounds(31*j + 1, 31*i + 1, 30, 30);
				this.player2cells[i][j] = cell;
				this.player2Panel.add(cell);
			}
		}
	}
	
	private void initMap() {
		cells = new JLabel[10][10];
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				JLabel cell = new JLabel();
				cell.setBackground(Color.white);
				cell.setOpaque(true);
				cell.setBounds(31*j + 1, 31*i + 1, 30, 30);
				cells[i][j] = cell;
				this.panel.add(cell);
			}
		}
		
	}
	
	private void clearFrame() {
		getContentPane().removeAll();
		getContentPane().setLayout(null);
	}
	
	private boolean coordinatesAreInRange(int x, int y) {
		if (0 <= x && x <= 9 && 0 <= y && y <= 9) {
			return true;
		}
		return false;
	}
	
	public JLabel getCell(int x, int y) {
		if (coordinatesAreInRange(x, y)) {
			return this.cells[y][x];
		}
		return null;
	}
}
