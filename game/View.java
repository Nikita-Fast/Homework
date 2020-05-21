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
	
	public View() {
		basicInitialisation();
	}
	
	public void uptadeFrame() {
		clearFrame();
		addMap();
		repaint();
	}
	
	private void basicInitialisation() {
		initStartFrame();
		//addStartButton();
	}
	
	private void initStartFrame() {
		setVisible(true);
		setBounds(300, 200, 1000, 700);
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
	
	private void createMap() {
		JPanel panel = new JPanel();
		panel.setBounds(83, 180, 311, 311);
		panel.setBackground(Color.black);
		panel.setLayout(null);
		this.panel = panel;
		
		initMap();
	}
	
	private void initMap() {
		cells = new JLabel[10][10];
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				JLabel cell = new JLabel();
				/*
				if ((i+j) % 3 == 0) {
					cell.setBackground(Color.white);
				}
				else {
					cell.setBackground(Color.red);
				}*/
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
