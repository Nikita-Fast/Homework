package game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI {

	private JFrame frame;
	private JPanel leftPanel;
	private JLabel[][] leftCells;
	public Matrix leftMatrix;
	private JTextField textField;
	
	public GUI() {
		initialize();
		initLeftPanel();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 200, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		leftPanel = new JPanel();
		leftPanel.setBounds(83, 180, 311, 311);
		leftPanel.setBackground(Color.black);
		frame.getContentPane().add(leftPanel);
		leftPanel.setLayout(null);
		
		JButton btnNewButton = new JButton("New game");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(514, 153, 219, 111);
		frame.getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(572, 352, 286, 231);
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		textField.setText("123");
		panel.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel.add(btnNewButton_1, BorderLayout.NORTH);
		frame.setVisible(true);
	}
	
	private void initLeftPanel() {
		leftCells = new JLabel[10][10];
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				JLabel cell = new JLabel();
				if ((i+j) % 3 == 0) {
					cell.setBackground(Color.white);
				}
				else {
					cell.setBackground(Color.red);
				}
				cell.setOpaque(true);
				cell.setBounds(31*j + 1, 31*i + 1, 30, 30);
				leftCells[i][j] = cell;
				leftPanel.add(cell);
			}
		}
		
	}
	/*
	public void drawLeftPanel() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				
				if (leftMatrix.getCell(i, j).getIsOpenState() == false) {
					
					leftCells[i][j].setBackground(Color.white);
				}
				else {
					if (leftMatrix.getCell(i, j).getState() == State.empty) {
						leftCells[i][j].setBackground(Color.gray);
				    }
					else {
						leftCells[i][j].setBackground(Color.red);
					}
			    }
			}
		}	
	}
	*/
	
	public void drawLeftPanel() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				
				
					
	
				
				
					if (leftMatrix.getCell(i, j).getState() == State.empty) {
						leftCells[i][j].setBackground(Color.gray);
				    }
					else {
						leftCells[i][j].setBackground(Color.red);
					}
			    }
			
		}	
	}
	
	
	public JPanel getLeftPanel() {
		return this.leftPanel;
	}
}
