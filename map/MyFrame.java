package map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyFrame  {
	private JFrame frmBattleships;
	public JPanel leftPanel;
	public JPanel rightPanel;
	private JLabel[][] leftCells;
	private JLabel[][] rightCells;
	
	private Matrix map1;
	private Matrix map2;
	
	public MyFrame() {
		initialize();
	}
	
	private void initialize() {
		frmBattleships = new JFrame();
		frmBattleships.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Nick Fast\\IdeaProjects\\BattleShips\\res\\images\\icon.jpg"));
		frmBattleships.setTitle("Battleships");
		frmBattleships.setBounds(100, 100, 993, 599);
		frmBattleships.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBattleships.getContentPane().setLayout(null);
		
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.BLACK);
		leftPanel.setBounds(67, 67, 311, 311);
		frmBattleships.getContentPane().add(leftPanel);
		leftPanel.setLayout(new GridLayout(10, 10, 1, 1));
		
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.BLACK);
		rightPanel.setBounds(580, 67, 311, 311);
		frmBattleships.getContentPane().add(rightPanel);
		rightPanel.setLayout(new GridLayout(10, 10, 1, 1));
		
		setUpCells();
		
		JLabel lblNewLabel = new JLabel("Your map");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(157, 29, 109, 25);
		frmBattleships.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Oponent's map");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(670, 23, 139, 36);
		frmBattleships.getContentPane().add(lblNewLabel_1);
		
		//refreshCells();
		/*
		leftPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX() / 31;
				int y = e.getY() / 31;
				if (x == 10) {
					x = 9;
				}
				if (y == 10) {
					y = 9;
				}
				Container comp = leftCells[y][x];
				comp.setBackground(Color.cyan);
			}
		});  */
		frmBattleships.setVisible(true);
	}
	/*
	public Coord getNewCoordOnLeftMapUseMouse() {// этот метод не может ничего возвращать, значит действие выстрел должно просиходить внутри
												// значит придется переносить код в другие классы и за основу брат ьрисование фрейма
		leftPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX() / 31;
				int y = e.getY() / 31;
				if (x == 10) {
					x = 9;
				}
				if (y == 10) {
					y = 9;
				}
				//
				
			}
		});
		
	} */
	
	public void setMatrix(Matrix matrix, Players player) {
		if (player == Players.first) {
			map1 = matrix;
		}
		if (player == Players.second) {
			map2 = matrix;
		}
	}

	public void refreshCells() {
		if (map1 != null) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Coord coord = new Coord(i, j);
				
				if (map1.getCell(coord).getIsOpenedState() == false) {
					leftCells[i][j].setBackground(Color.WHITE);
				}
				else {
					if (map1.getCell(coord).getState() == State.empty) {
						leftCells[i][j].setBackground(Color.gray);
					}
					else {
						leftCells[i][j].setBackground(Color.cyan);
					}
				}
			}
		}
		}
		
		if (map2 != null) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					Coord coord = new Coord(i, j);
					
					if (map2.getCell(coord).getIsOpenedState() == false) {
						rightCells[i][j].setBackground(Color.WHITE);
					}
					else {
						if (map2.getCell(coord).getState() == State.empty) {
							rightCells[i][j].setBackground(Color.gray);
						}
						else {
							rightCells[i][j].setBackground(Color.cyan);
						}
					}
				}
			}
		}
	}
	
	private void setUpCells() {
		leftCells = new JLabel[10][10];
		rightCells = new JLabel[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				leftCells[i][j] = new JLabel();
				leftCells[i][j].setBackground(Color.white);
				leftCells[i][j].setOpaque(true);
				
				rightCells[i][j] = new JLabel();
				rightCells[i][j].setBackground(Color.white);
				rightCells[i][j].setOpaque(true);
			}
		}
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				/*
				Container comp = leftCells[i][j];
				comp.setBounds(1 + j * 30, 1 + i * 30, 30, 30);
				panel.add(comp); */
				
				Container comp = leftCells[i][j];
				comp.setBounds(1 + j * 30, 1 + i * 30, 30, 30);
				leftPanel.add(comp);
				
				Container comp2 = rightCells[i][j];
				comp2.setBounds(1 + j * 30, 1 + i * 30, 30, 30);
				rightPanel.add(comp2); 
			}
		}
	}
}

























